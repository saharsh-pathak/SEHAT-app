package com.example.sehat.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import com.example.sehat.speech.AudioRecorder
import com.example.sehat.speech.ModelManager
import com.example.sehat.speech.SherpaSTTEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

class VoiceManager(private val context: Context) : TextToSpeech.OnInitListener, RecognitionListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var speechRecognizer: SpeechRecognizer? = null
    private var isTtsReady = false
    private var pendingSpeechText: String? = null
    private var pendingSpeechLang: String? = null

    private val modelManager = ModelManager(context)
    private val audioRecorder = AudioRecorder()
    private var sherpaEngine: SherpaSTTEngine? = null

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var audioCollectJob: Job? = null

    var onSpeechResults: ((String) -> Unit)? = null
    var onSpeechError: ((String) -> Unit)? = null
    var onListeningStateChanged: ((Boolean) -> Unit)? = null
    var onDownloadProgress: ((Int, String) -> Unit)? = null
    var onSpeechComplete: (() -> Unit)? = null
    var onSpeechStart: (() -> Unit)? = null

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            setupUtteranceListener()
            val text = pendingSpeechText
            val lang = pendingSpeechLang
            if (!text.isNullOrBlank() && !lang.isNullOrBlank()) {
                pendingSpeechText = null
                pendingSpeechLang = null
                speak(text, lang)
            }
        } else {
            onSpeechError?.invoke("Text-to-Speech initialization failed ($status)")
        }
    }

    private fun setupUtteranceListener() {
        tts?.setOnUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                scope.launch { onSpeechStart?.invoke() }
            }

            override fun onDone(utteranceId: String?) {
                scope.launch { onSpeechComplete?.invoke() }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                scope.launch { onSpeechComplete?.invoke() }
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                scope.launch { onSpeechComplete?.invoke() }
            }
        })
    }

    fun speak(text: String, language: String) {
        if (text.isBlank()) return
        if (!isTtsReady) {
            pendingSpeechText = text
            pendingSpeechLang = language
            return
        }

        // Only Hindi and Marathi STT/TTS work, rest all just for show (fallback to Hindi)
        val targetLocale = when (language.lowercase()) {
            "marathi", "mr" -> Locale("mr", "IN")
            "hindi", "hi" -> Locale("hi", "IN")
            else -> Locale("hi", "IN")
        }

        var result = tts?.setLanguage(targetLocale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            // Fallback to Hindi if Marathi voice data is missing on the phone
            result = tts?.setLanguage(Locale("hi", "IN"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.getDefault())
            }
        }
        tts?.setSpeechRate(0.95f)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SEHAT_TTS")
    }

    fun stopSpeak() {
        pendingSpeechText = null
        pendingSpeechLang = null
        tts?.stop()
    }

    fun isModelReady(language: String): Boolean {
        return modelManager.isModelAvailable(language)
    }

    fun downloadModel(language: String, onComplete: (Boolean) -> Unit) {
        modelManager.downloadModel(
            language = language,
            onProgress = { progress, status ->
                onDownloadProgress?.invoke(progress, status)
            },
            onComplete = onComplete
        )
    }

    fun startListening(language: String) {
        stopSpeak()

        if (modelManager.isModelAvailable(language)) {
            startSherpaOfflineListening(language)
        } else if (SpeechRecognizer.isRecognitionAvailable(context)) {
            startAndroidSpeechRecognizer(language)
        } else {
            onSpeechError?.invoke("Model for $language not downloaded. Tap to download offline STT model.")
        }
    }

    private fun startSherpaOfflineListening(language: String) {
        if (sherpaEngine == null) {
            sherpaEngine = SherpaSTTEngine(context)
        }
        sherpaEngine?.start(language)

        audioRecorder.startRecording(scope)
        audioCollectJob = audioRecorder.audioFlow
            .onEach { samples ->
                sherpaEngine?.feedAudio(samples)
                val partial = sherpaEngine?.getPartialResult().orEmpty()
                if (partial.isNotBlank()) {
                    onSpeechResults?.invoke(partial)
                }
            }
            .launchIn(scope)

        onListeningStateChanged?.invoke(true)
    }

    private fun startAndroidSpeechRecognizer(language: String) {
        try {
            speechRecognizer?.destroy()
        } catch (_: Exception) {}
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(this@VoiceManager)
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            val langTag = when (language.lowercase()) {
                "marathi", "mr" -> "mr-IN"
                "hindi", "hi" -> "hi-IN"
                else -> "hi-IN"
            }
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, langTag)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        try {
            speechRecognizer?.startListening(intent)
            onListeningStateChanged?.invoke(true)
        } catch (e: Exception) {
            onSpeechError?.invoke("Could not start speech recognition: ${e.message}")
            onListeningStateChanged?.invoke(false)
        }
    }

    fun stopListening() {
        if (audioRecorder.isRecording) {
            audioRecorder.stopRecording()
            audioCollectJob?.cancel()
            audioCollectJob = null
            sherpaEngine?.stop()
            val finalResult = sherpaEngine?.getFinalResult().orEmpty()
            if (finalResult.isNotBlank()) {
                onSpeechResults?.invoke(finalResult)
            }
        }
        speechRecognizer?.stopListening()
        onListeningStateChanged?.invoke(false)
    }

    fun destroy() {
        tts?.stop()
        tts?.shutdown()
        audioRecorder.stopRecording()
        sherpaEngine?.release()
        speechRecognizer?.destroy()
        speechRecognizer = null
    }

    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {
        onListeningStateChanged?.invoke(false)
    }

    override fun onError(error: Int) {
        onListeningStateChanged?.invoke(false)
        val msg = when (error) {
            SpeechRecognizer.ERROR_NETWORK, SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network unavailable for online STT"
            SpeechRecognizer.ERROR_NO_MATCH -> "No speech recognized"
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            else -> "Speech recognition notice ($error)"
        }
        onSpeechError?.invoke(msg)
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            onSpeechResults?.invoke(matches[0])
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            onSpeechResults?.invoke(matches[0])
        }
    }

    override fun onEvent(eventType: Int, params: Bundle?) {}
}

