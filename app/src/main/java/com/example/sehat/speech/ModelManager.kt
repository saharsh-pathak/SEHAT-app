package com.example.sehat.speech

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Manages model downloads and storage for offline IndicConformer STT and Silero VAD models.
 * Models are downloaded on-demand from HuggingFace and stored in app's files directory.
 */
class ModelManager(private val context: Context) {

    companion object {
        private const val BASE_URL = "https://huggingface.co/parismitaglobalsolutions/indicconformer-sherpa-onnx/resolve/main"

        private val MODEL_FILES = mapOf(
            "hi" to listOf("encoder.int8.onnx", "decoder.int8.onnx", "joiner.int8.onnx", "tokens.txt"),
            "mr" to listOf("encoder.int8.onnx", "decoder.int8.onnx", "joiner.int8.onnx", "tokens.txt")
        )
    }

    fun getModelDir(language: String): File {
        val langCode = normalizeLang(language)
        val dir = File(context.filesDir, "models/stt/$langCode")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun getVadModelFile(): File {
        val dir = File(context.filesDir, "models/vad")
        if (!dir.exists()) dir.mkdirs()
        return File(dir, "silero_vad.onnx")
    }

    fun isModelAvailable(language: String): Boolean {
        val langCode = normalizeLang(language)
        val files = MODEL_FILES[langCode] ?: return false
        val dir = getModelDir(langCode)
        return files.all { File(dir, it).exists() && File(dir, it).length() > 0 }
    }

    fun isVadAvailable(): Boolean {
        val file = getVadModelFile()
        return file.exists() && file.length() > 0
    }

    fun downloadModel(
        language: String,
        onProgress: (progressPercent: Int, statusText: String) -> Unit,
        onComplete: (Boolean) -> Unit
    ) {
        val langCode = normalizeLang(language)
        val files = MODEL_FILES[langCode]
        if (files == null) {
            onComplete(false)
            return
        }

        val targetDir = getModelDir(langCode)
        val totalFiles = files.size
        var downloadedFiles = 0

        Thread {
            try {
                for ((index, fileName) in files.withIndex()) {
                    val fileUrl = "$BASE_URL/$langCode/$fileName"
                    val destFile = File(targetDir, fileName)

                    if (destFile.exists() && destFile.length() > 0) {
                        downloadedFiles++
                        continue
                    }

                    onProgress(
                        ((index.toFloat() / totalFiles) * 100).toInt(),
                        "Downloading $fileName for ${langCode.uppercase()}..."
                    )

                    val url = URL(fileUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.connectTimeout = 15000
                    connection.readTimeout = 30000
                    connection.connect()

                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        onComplete(false)
                        return@Thread
                    }

                    val inputStream = connection.inputStream
                    val outputStream = FileOutputStream(destFile)
                    val buffer = ByteArray(8192)
                    var bytesRead: Int

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }

                    outputStream.close()
                    inputStream.close()
                    downloadedFiles++
                }

                onProgress(100, "Download complete")
                onComplete(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false)
            }
        }.start()
    }

    private fun normalizeLang(language: String): String {
        return when (language.lowercase()) {
            "marathi", "mr" -> "mr"
            "hindi", "hi" -> "hi"
            else -> "hi"
        }
    }
}
