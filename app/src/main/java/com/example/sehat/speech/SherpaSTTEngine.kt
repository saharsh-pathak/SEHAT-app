package com.example.sehat.speech

import android.content.Context
import java.io.File

/**
 * Interface and stub implementation for Sherpa-ONNX streaming STT engine.
 * Wraps sherpa-onnx OnlineRecognizer for continuous streaming transcription.
 * Falls back safely when native binary is missing.
 */
interface STTEngine {
    fun isAvailable(): Boolean
    fun start(language: String): Boolean
    fun feedAudio(samples: FloatArray)
    fun getPartialResult(): String
    fun getFinalResult(): String
    fun stop()
    fun release()
}

class SherpaSTTEngine(private val context: Context) : STTEngine {

    private val modelManager = ModelManager(context)
    private var currentLanguage = "hi"
    private var isEngineRunning = false
    private val transcriptBuffer = StringBuilder()

    override fun isAvailable(): Boolean {
        return modelManager.isModelAvailable(currentLanguage)
    }

    override fun start(language: String): Boolean {
        currentLanguage = language
        transcriptBuffer.clear()
        isEngineRunning = true
        return true
    }

    override fun feedAudio(samples: FloatArray) {
        if (!isEngineRunning) return
        // Streaming audio processing logic
    }

    override fun getPartialResult(): String {
        return transcriptBuffer.toString()
    }

    override fun getFinalResult(): String {
        return transcriptBuffer.toString()
    }

    override fun stop() {
        isEngineRunning = false
    }

    override fun release() {
        stop()
    }

    fun appendPartialText(text: String) {
        if (text.isNotBlank()) {
            transcriptBuffer.append(text).append(" ")
        }
    }
}
