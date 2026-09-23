package com.example.sehat.util

import android.content.Context
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClinicalQuestion(
    val id: Int,
    val textEn: String,
    val textMr: String,
    val textHi: String,
    val relatedSymptom: String
)

val SCREENING_QUESTIONS = listOf(
    ClinicalQuestion(
        id = 1,
        textEn = "Are you experiencing any fever or high body temperature?",
        textMr = "आपल्याला ताप किंवा अंगात उष्णता जाणवत आहे का?",
        textHi = "क्या आपको बुखार या शरीर में तेज गर्मी महसूस हो रही है?",
        relatedSymptom = "Fever"
    ),
    ClinicalQuestion(
        id = 2,
        textEn = "Do you have a persistent cough or difficulty breathing?",
        textMr = "आपल्याला सतत खोकला किंवा श्वास घेण्यास अडचण येत आहे का?",
        textHi = "क्या आपको लगातार खांसी या सांस लेने में तकलीफ हो रही है?",
        relatedSymptom = "Cough"
    ),
    ClinicalQuestion(
        id = 3,
        textEn = "Do you feel any chest pain, heavy pressure, or palpitations?",
        textMr = "आपल्याला छातीत दुखणे, जडपणा किंवा धडधड जाणवत आहे का?",
        textHi = "क्या आपको सीने में दर्द, भारीपन या घबराहट महसूस हो रही है?",
        relatedSymptom = "Chest Pain"
    ),
    ClinicalQuestion(
        id = 4,
        textEn = "Have you experienced dizziness, fainting, or severe headaches?",
        textMr = "आपल्याला चक्कर येणे, डोके दुखणे किंवा भोवळ आल्यासारखे वाटते का?",
        textHi = "क्या आपको चक्कर आना, सिरदर्द या बेहोशी जैसा लगता है?",
        relatedSymptom = "Dizziness"
    ),
    ClinicalQuestion(
        id = 5,
        textEn = "How many days have you been experiencing these symptoms?",
        textMr = "ही लक्षणे आपल्याला किती दिवसांपासून जाणवत आहेत?",
        textHi = "यह लक्षण आपको कितने दिनों से दिखाई दे रहे हैं?",
        relatedSymptom = "Fatigue"
    ),
    ClinicalQuestion(
        id = 6,
        textEn = "Do you have a known history of high blood pressure or diabetes?",
        textMr = "आपल्याला आधीपासून उच्च रक्तदाब किंवा मधुमेहाचा आजार आहे का?",
        textHi = "क्या आपको पहले से हाई ब्लड प्रेशर या शुगर की बीमारी है?",
        relatedSymptom = "Hypertension History"
    ),
    ClinicalQuestion(
        id = 7,
        textEn = "Are you experiencing any nausea, vomiting, or stomach discomfort?",
        textMr = "आपल्याला मळमळ, उलट्या किंवा पोटात दुखणे असा त्रास आहे का?",
        textHi = "क्या आपको उल्टी, जी मिचलाना या पेट में दर्द है?",
        relatedSymptom = "Nausea"
    ),
    ClinicalQuestion(
        id = 8,
        textEn = "Have you noticed any swelling in your feet, ankles, or face?",
        textMr = "आपल्या पायावर, घोट्यावर किंवा चेहऱ्यावर सूज आली आहे का?",
        textHi = "क्या आपके पैरों या चेहरे पर सूजन आई है?",
        relatedSymptom = "Body Swelling"
    ),
    ClinicalQuestion(
        id = 9,
        textEn = "Have you had sudden weakness or loss of grip in hands or legs?",
        textMr = "हात किंवा पायात अचानक अशक्तपणा किंवा पकड सुटल्यासारखे वाटते का?",
        textHi = "क्या हाथ या पैरों में अचानक कमजोरी या सुन्नपन लगा है?",
        relatedSymptom = "Weakness"
    ),
    ClinicalQuestion(
        id = 10,
        textEn = "Are you able to sleep comfortably without breathing difficulty?",
        textMr = "आपण रात्री श्वासाच्या त्रासाशिवाय व्यवस्थित झोपू शकता का?",
        textHi = "क्या आप रात में बिना सांस की परेशानी के आराम से सो पाते हैं?",
        relatedSymptom = "Sleep Disturbance"
    ),
    ClinicalQuestion(
        id = 11,
        textEn = "Do you have any known drug allergies or chronic medical conditions?",
        textMr = "आपल्याला कोणत्याही औषधांची ॲलर्जी किंवा इतर जुनाट आजार आहे का?",
        textHi = "क्या आपको किसी दवा से एलर्जी या अन्य पुरानी बीमारी है?",
        relatedSymptom = "Allergy None"
    ),
    ClinicalQuestion(
        id = 12,
        textEn = "Has anyone in your immediate family suffered from early heart attacks?",
        textMr = "कुटुंबात कोणाला कमी वयात हृदयविकाराचा झटका आला होता का?",
        textHi = "क्या परिवार में किसी को दिल का दौरा पड़ने की बीमारी रही है?",
        relatedSymptom = "Family Cardiac History"
    )
)

class SpeechStreamingManager(
    private val context: Context,
    private val voiceManager: VoiceManager,
    private val coroutineScope: CoroutineScope
) {
    private val _selectedLanguage = MutableStateFlow("mr")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(1)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    val totalQuestions: Int = SCREENING_QUESTIONS.size

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    // Streaming Kokoro TTS follow-up question text
    private val _streamingQuestionWords = MutableStateFlow<List<String>>(emptyList())
    val streamingQuestionWords: StateFlow<List<String>> = _streamingQuestionWords.asStateFlow()

    private val _highlightedQuestionWordIndex = MutableStateFlow(-1)
    val highlightedQuestionWordIndex: StateFlow<Int> = _highlightedQuestionWordIndex.asStateFlow()

    private val _fullSpokenQuestionText = MutableStateFlow("")
    val fullSpokenQuestionText: StateFlow<String> = _fullSpokenQuestionText.asStateFlow()

    // Real live STT patient response transcript (Android SpeechRecognizer / Sherpa)
    private val _streamingTranscriptWords = MutableStateFlow<List<String>>(emptyList())
    val streamingTranscriptWords: StateFlow<List<String>> = _streamingTranscriptWords.asStateFlow()

    private val _highlightedTranscriptWordIndex = MutableStateFlow(-1)
    val highlightedTranscriptWordIndex: StateFlow<Int> = _highlightedTranscriptWordIndex.asStateFlow()

    private val _finalTranscript = MutableStateFlow("")
    val finalTranscript: StateFlow<String> = _finalTranscript.asStateFlow()

    private var questionStreamingJob: Job? = null

    init {
        voiceManager.onSpeechResults = { text ->
            if (text.isNotBlank()) {
                _finalTranscript.value = text
                _streamingTranscriptWords.value = text.split(" ").filter { it.isNotBlank() }
            }
        }
        voiceManager.onListeningStateChanged = { listening ->
            _isListening.value = listening
        }
        voiceManager.onSpeechError = { _ ->
            _isListening.value = false
        }
    }

    fun setLanguage(langCode: String) {
        _selectedLanguage.value = langCode
    }

    fun startScreening(initialQuestionIndex: Int = 1) {
        _currentQuestionIndex.value = initialQuestionIndex.coerceIn(1, totalQuestions)
        streamQuestion(_currentQuestionIndex.value)
    }

    fun getCurrentQuestion(): ClinicalQuestion {
        val idx = (_currentQuestionIndex.value - 1).coerceIn(0, SCREENING_QUESTIONS.size - 1)
        return SCREENING_QUESTIONS[idx]
    }

    fun getQuestionTextForLang(q: ClinicalQuestion, lang: String): String {
        return when (lang.lowercase()) {
            "mr", "marathi" -> q.textMr
            "hi", "hindi" -> q.textHi
            else -> q.textEn
        }
    }

    /**
     * Kokoro TTS Streaming Question Pipeline
     * Streams tokens progressively in sync with spoken voice.
     */
    fun streamQuestion(questionNum: Int) {
        questionStreamingJob?.cancel()
        voiceManager.stopListening()
        _isListening.value = false

        _currentQuestionIndex.value = questionNum.coerceIn(1, totalQuestions)
        val q = getCurrentQuestion()
        val textToSpeak = getQuestionTextForLang(q, _selectedLanguage.value)
        _fullSpokenQuestionText.value = textToSpeak

        val words = textToSpeak.split(" ").filter { it.isNotBlank() }
        _streamingQuestionWords.value = emptyList()
        _highlightedQuestionWordIndex.value = -1

        _isSpeaking.value = true

        // Start Kokoro TTS
        voiceManager.speak(textToSpeak, _selectedLanguage.value)

        questionStreamingJob = coroutineScope.launch {
            val visibleTokens = mutableListOf<String>()
            for (i in words.indices) {
                visibleTokens.add(words[i])
                _streamingQuestionWords.value = visibleTokens.toList()
                _highlightedQuestionWordIndex.value = i
                delay(120)
            }
            _highlightedQuestionWordIndex.value = -1
            _isSpeaking.value = false

            // Auto-transition to real mic listening once question reading finishes
            delay(300)
            startListening()
        }
    }

    fun startListening() {
        questionStreamingJob?.cancel()
        _isSpeaking.value = false
        _isListening.value = true
        voiceManager.startListening(_selectedLanguage.value)
    }

    fun stopListening() {
        _isListening.value = false
        voiceManager.stopListening()
    }

    fun toggleMicrophone() {
        if (_isListening.value) {
            stopListening()
        } else if (_isSpeaking.value) {
            voiceManager.stopSpeak()
            questionStreamingJob?.cancel()
            _isSpeaking.value = false
            _streamingQuestionWords.value = _fullSpokenQuestionText.value.split(" ").filter { it.isNotBlank() }
            _highlightedQuestionWordIndex.value = -1
            startListening()
        } else {
            startListening()
        }
    }

    fun repeatQuestion() {
        streamQuestion(_currentQuestionIndex.value)
    }

    fun skipQuestion() {
        if (_currentQuestionIndex.value < totalQuestions) {
            streamQuestion(_currentQuestionIndex.value + 1)
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < totalQuestions) {
            streamQuestion(_currentQuestionIndex.value + 1)
        }
    }

    fun stopScreening() {
        voiceManager.stopSpeak()
        voiceManager.stopListening()
        questionStreamingJob?.cancel()
        _isSpeaking.value = false
        _isListening.value = false
        _isProcessing.value = false
    }

    fun updateManualTranscript(text: String) {
        _finalTranscript.value = text
        _streamingTranscriptWords.value = text.split(" ").filter { it.isNotBlank() }
    }
}
