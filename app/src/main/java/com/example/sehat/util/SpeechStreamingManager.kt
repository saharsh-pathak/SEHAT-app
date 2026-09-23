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
    val sampleResponseMr: String,
    val sampleResponseHi: String,
    val sampleResponseEn: String,
    val relatedSymptom: String
)

val SCREENING_QUESTIONS = listOf(
    ClinicalQuestion(
        id = 1,
        textEn = "Are you experiencing any fever or high body temperature?",
        textMr = "आपल्याला ताप किंवा अंगात उष्णता जाणवत आहे का?",
        textHi = "क्या आपको बुखार या शरीर में तेज गर्मी महसूस हो रही है?",
        sampleResponseMr = "होय, मला दोन दिवसांपासून खूप ताप येत आहे.",
        sampleResponseHi = "हाँ, मुझे दो दिनों से काफी तेज बुखार आ रहा है।",
        sampleResponseEn = "Yes, I have had a high fever for the last two days.",
        relatedSymptom = "Fever"
    ),
    ClinicalQuestion(
        id = 2,
        textEn = "Do you have a persistent cough or difficulty breathing?",
        textMr = "आपल्याला सतत खोकला किंवा श्वास घेण्यास अडचण येत आहे का?",
        textHi = "क्या आपको लगातार खांसी या सांस लेने में तकलीफ हो रही है?",
        sampleResponseMr = "होय, छातीत दुखते आणि श्वास घेताना धाप लागते.",
        sampleResponseHi = "हाँ, सीने में दर्द है और सांस फूलती है।",
        sampleResponseEn = "Yes, coughing continuously and having shortness of breath.",
        relatedSymptom = "Cough"
    ),
    ClinicalQuestion(
        id = 3,
        textEn = "Do you feel any chest pain, heavy pressure, or palpitations?",
        textMr = "आपल्याला छातीत दुखणे, जडपणा किंवा धडधड जाणवत आहे का?",
        textHi = "क्या आपको सीने में दर्द, भारीपन या घबराहट महसूस हो रही है?",
        sampleResponseMr = "छातीच्या डाव्या बाजूला जडपणा आणि धडधड होते.",
        sampleResponseHi = "सीने के बाईं तरफ भारीपन और घबराहट है।",
        sampleResponseEn = "Severe heaviness in left chest with palpitations.",
        relatedSymptom = "Chest Pain"
    ),
    ClinicalQuestion(
        id = 4,
        textEn = "Have you experienced dizziness, fainting, or severe headaches?",
        textMr = "आपल्याला चक्कर येणे, डोके दुखणे किंवा भोवळ आल्यासारखे वाटते का?",
        textHi = "क्या आपको चक्कर आना, सिरदर्द या बेहोशी जैसा लगता है?",
        sampleResponseMr = "उठताना डोळ्यापुढे अंधारी येते आणि डोके दुखते.",
        sampleResponseHi = "खड़े होने पर चक्कर आता है और सिर भारी रहता है।",
        sampleResponseEn = "Frequent dizziness when standing up and severe headache.",
        relatedSymptom = "Dizziness"
    ),
    ClinicalQuestion(
        id = 5,
        textEn = "How many days have you been experiencing these symptoms?",
        textMr = "ही लक्षणे आपल्याला किती दिवसांपासून जाणवत आहेत?",
        textHi = "यह लक्षण आपको कितने दिनों से दिखाई दे रहे हैं?",
        sampleResponseMr = "सुमारे तीन ते चार दिवसांपासून त्रास जास्त वाढला आहे.",
        sampleResponseHi = "लगभग तीन से चार दिनों से परेशानी काफी बढ़ गई है।",
        sampleResponseEn = "Around three to four days continuously.",
        relatedSymptom = "Fatigue"
    ),
    ClinicalQuestion(
        id = 6,
        textEn = "Do you have a known history of high blood pressure or diabetes?",
        textMr = "आपल्याला आधीपासून उच्च रक्तदाब किंवा मधुमेहाचा आजार आहे का?",
        textHi = "क्या आपको पहले से हाई ब्लड प्रेशर या शुगर की बीमारी है?",
        sampleResponseMr = "होय, उच्च रक्तदाबाची गोळी घेतो पण काल घेतली नाही.",
        sampleResponseHi = "हाँ, बीपी की दवा चलती है लेकिन कल नहीं खाई थी।",
        sampleResponseEn = "Yes, known hypertension on irregular medication.",
        relatedSymptom = "Hypertension History"
    ),
    ClinicalQuestion(
        id = 7,
        textEn = "Are you experiencing any nausea, vomiting, or stomach discomfort?",
        textMr = "आपल्याला मळमळ, उलट्या किंवा पोटात दुखणे असा त्रास आहे का?",
        textHi = "क्या आपको उल्टी, जी मिचलाना या पेट में दर्द है?",
        sampleResponseMr = "सकाळी उलट्या झाल्या आणि भूक लागत नाही.",
        sampleResponseHi = "सुबह उल्टी हुई थी और कुछ खाने का मन नहीं है।",
        sampleResponseEn = "Mild nausea and loss of appetite since yesterday.",
        relatedSymptom = "Nausea"
    ),
    ClinicalQuestion(
        id = 8,
        textEn = "Have you noticed any swelling in your feet, ankles, or face?",
        textMr = "आपल्या पायावर, घोट्यावर किंवा चेहऱ्यावर सूज आली आहे का?",
        textHi = "क्या आपके पैरों या चेहरे पर सूजन आई है?",
        sampleResponseMr = "संध्याकाळी दोन्ही पायांवर सूज जाणवते.",
        sampleResponseHi = "शाम को दोनों पैरों में हल्की सूजन आ जाती है।",
        sampleResponseEn = "Pedal edema noticed in both ankles in evenings.",
        relatedSymptom = "Body Swelling"
    ),
    ClinicalQuestion(
        id = 9,
        textEn = "Have you had sudden weakness or loss of grip in hands or legs?",
        textMr = "हात किंवा पायात अचानक अशक्तपणा किंवा पकड सुटल्यासारखे वाटते का?",
        textHi = "क्या हाथ या पैरों में अचानक कमजोरी या सुन्नपन लगा है?",
        sampleResponseMr = "नाही, पण शरीर खूप थकून जाते.",
        sampleResponseHi = "नहीं, लेकिन पूरा बदन बहुत थका हुआ रहता है।",
        sampleResponseEn = "No weakness, but general physical exhaustion.",
        relatedSymptom = "Weakness"
    ),
    ClinicalQuestion(
        id = 10,
        textEn = "Are you able to sleep comfortably without breathing difficulty?",
        textMr = "आपण रात्री श्वासाच्या त्रासाशिवाय व्यवस्थित झोपू शकता का?",
        textHi = "क्या आप रात में बिना सांस की परेशानी के आराम से सो पाते हैं?",
        sampleResponseMr = "रात्री झोपताना श्वास गुदमरतो, उठून बसावे लागते.",
        sampleResponseHi = "रात को सांस फूलने से नींद खुल जाती है।",
        sampleResponseEn = "Orthopnea present, wakes up gasping at night.",
        relatedSymptom = "Sleep Disturbance"
    ),
    ClinicalQuestion(
        id = 11,
        textEn = "Do you have any known drug allergies or chronic medical conditions?",
        textMr = "आपल्याला कोणत्याही औषधांची ॲलर्जी किंवा इतर जुनाट आजार आहे का?",
        textHi = "क्या आपको किसी दवा से एलर्जी या अन्य पुरानी बीमारी है?",
        sampleResponseMr = "नाही, कोणतीही ॲलर्जी नाही.",
        sampleResponseHi = "नहीं, कोई एलर्जी नहीं है।",
        sampleResponseEn = "No known drug allergies.",
        relatedSymptom = "Allergy None"
    ),
    ClinicalQuestion(
        id = 12,
        textEn = "Has anyone in your immediate family suffered from early heart attacks?",
        textMr = "कुटुंबात कोणाला कमी वयात हृदयविकाराचा झटका आला होता का?",
        textHi = "क्या परिवार में किसी को दिल का दौरा पड़ने की बीमारी रही है?",
        sampleResponseMr = "वडिलांना उच्च रक्तदाब आणि हृदयविकार होता.",
        sampleResponseHi = "पिताजी को दिल की बीमारी और बीपी था।",
        sampleResponseEn = "Father had hypertension and coronary artery disease.",
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

    private val _currentQuestionIndex = MutableStateFlow(1) // 1-based, 2 of 12 default
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

    // Streaming IndicConformer STT patient response transcript
    private val _streamingTranscriptWords = MutableStateFlow<List<String>>(emptyList())
    val streamingTranscriptWords: StateFlow<List<String>> = _streamingTranscriptWords.asStateFlow()

    private val _highlightedTranscriptWordIndex = MutableStateFlow(-1)
    val highlightedTranscriptWordIndex: StateFlow<Int> = _highlightedTranscriptWordIndex.asStateFlow()

    private val _finalTranscript = MutableStateFlow("")
    val finalTranscript: StateFlow<String> = _finalTranscript.asStateFlow()

    private val _capturedSymptoms = MutableStateFlow<List<String>>(listOf("Fever", "Cough", "Chest Pain", "Breathlessness"))
    val capturedSymptoms: StateFlow<List<String>> = _capturedSymptoms.asStateFlow()

    private var questionStreamingJob: Job? = null
    private var transcriptStreamingJob: Job? = null

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

    fun getSampleResponseForLang(q: ClinicalQuestion, lang: String): String {
        return when (lang.lowercase()) {
            "mr", "marathi" -> q.sampleResponseMr
            "hi", "hindi" -> q.sampleResponseHi
            else -> q.sampleResponseEn
        }
    }

    /**
     * Kokoro TTS Streaming Question Pipeline
     * Streams tokens progressively in sync with spoken voice.
     */
    fun streamQuestion(questionNum: Int) {
        questionStreamingJob?.cancel()
        transcriptStreamingJob?.cancel()

        _currentQuestionIndex.value = questionNum.coerceIn(1, totalQuestions)
        val q = getCurrentQuestion()
        val textToSpeak = getQuestionTextForLang(q, _selectedLanguage.value)
        _fullSpokenQuestionText.value = textToSpeak

        val words = textToSpeak.split(" ").filter { it.isNotBlank() }
        _streamingQuestionWords.value = emptyList()
        _highlightedQuestionWordIndex.value = -1

        _isSpeaking.value = true
        _isListening.value = false

        // Start Kokoro TTS
        voiceManager.speak(textToSpeak, _selectedLanguage.value)

        questionStreamingJob = coroutineScope.launch {
            val visibleTokens = mutableListOf<String>()
            for (i in words.indices) {
                visibleTokens.add(words[i])
                _streamingQuestionWords.value = visibleTokens.toList()
                _highlightedQuestionWordIndex.value = i
                // Token streaming pace (~110ms per word matches natural Kokoro cadence)
                delay(120)
            }
            _highlightedQuestionWordIndex.value = -1
            _isSpeaking.value = false

            // Auto-transition to listening state after question completes
            delay(400)
            startPatientListeningSimulation()
        }
    }

    /**
     * IndicConformer Streaming STT Patient Voice Pipeline
     * Streams transcription word-by-word into the Live Transcription Card.
     */
    fun startPatientListeningSimulation() {
        transcriptStreamingJob?.cancel()
        _isListening.value = true
        _isSpeaking.value = false
        _isProcessing.value = false

        val q = getCurrentQuestion()
        val responseText = getSampleResponseForLang(q, _selectedLanguage.value)
        val words = responseText.split(" ").filter { it.isNotBlank() }

        _streamingTranscriptWords.value = emptyList()
        _highlightedTranscriptWordIndex.value = -1
        _finalTranscript.value = ""

        transcriptStreamingJob = coroutineScope.launch {
            delay(500) // Brief silence before patient starts answering
            val visibleTokens = mutableListOf<String>()

            for (i in words.indices) {
                if (!_isListening.value) break
                visibleTokens.add(words[i])
                _streamingTranscriptWords.value = visibleTokens.toList()
                _highlightedTranscriptWordIndex.value = i
                delay(160) // Streaming word tokens
            }

            _highlightedTranscriptWordIndex.value = -1
            _isListening.value = false
            _finalTranscript.value = responseText

            // Append symptom to captured list
            if (!capturedSymptoms.value.contains(q.relatedSymptom) && q.relatedSymptom.isNotBlank()) {
                _capturedSymptoms.value = _capturedSymptoms.value + q.relatedSymptom
            }
        }
    }

    fun toggleMicrophone() {
        if (_isListening.value) {
            // Stop listening & freeze transcript
            transcriptStreamingJob?.cancel()
            _isListening.value = false
            val currentWords = _streamingTranscriptWords.value
            if (currentWords.isNotEmpty()) {
                _finalTranscript.value = currentWords.joinToString(" ")
            } else {
                val q = getCurrentQuestion()
                _finalTranscript.value = getSampleResponseForLang(q, _selectedLanguage.value)
                _streamingTranscriptWords.value = _finalTranscript.value.split(" ")
            }
            _highlightedTranscriptWordIndex.value = -1
        } else if (_isSpeaking.value) {
            // Stop speaking & immediately listen
            voiceManager.stopSpeak()
            questionStreamingJob?.cancel()
            _isSpeaking.value = false
            _streamingQuestionWords.value = _fullSpokenQuestionText.value.split(" ")
            _highlightedQuestionWordIndex.value = -1
            startPatientListeningSimulation()
        } else {
            // Start fresh listening
            startPatientListeningSimulation()
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
        questionStreamingJob?.cancel()
        transcriptStreamingJob?.cancel()
        _isSpeaking.value = false
        _isListening.value = false
        _isProcessing.value = false
    }

    fun updateManualTranscript(text: String) {
        _finalTranscript.value = text
    }

    fun addSymptom(symptom: String) {
        if (!_capturedSymptoms.value.contains(symptom)) {
            _capturedSymptoms.value = _capturedSymptoms.value + symptom
        }
    }

    fun removeSymptom(symptom: String) {
        _capturedSymptoms.value = _capturedSymptoms.value - symptom
    }
}
