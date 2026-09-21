package com.example.sehat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehat.data.SehatDatabase
import com.example.sehat.data.entity.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CareEpisodeState(
    val episodeId: Long = 0,
    val abhaId: String = "",
    val voiceTranscript: String = "",
    val manualSymptoms: String = "",
    val selectedSymptomChips: List<String> = emptyList(),
    val bloodPressure: String = "120/80",
    val heartRate: Int = 88,
    val spo2: Int = 98,
    val temperature: Float = 37.8f,
    val bloodGlucose: Int = 110,
    val hemoglobin: Float = 11.2f,
    val weight: Float = 58.0f,
    val height: Float = 162.0f,
    val pregnancyTest: String = "Not Conducted",
    val malariaTest: String = "Not Conducted",
    val dengueTest: String = "Not Conducted",
    val urineTest: String = "Not Conducted",
    val tbScreening: String = "Not Conducted",
    val additionalNotes: String = "",
    val probableCondition: String = "Upper Respiratory Tract Infection (URTI)",
    val severity: String = "Mild",
    val recommendedFacility: String = "AAM-SHC (Sub-centre)",
    val recommendedProfessional: String = "Medical Worker",
    val justification: String = "• Symptoms are mild with no danger signs\n• Vitals are stable\n• Suitable for management at AAM-SHC level",
    val clinicalNotes: String = ""
)

class CareEpisodeViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _state = MutableStateFlow(CareEpisodeState())
    val state: StateFlow<CareEpisodeState> = _state.asStateFlow()

    fun startEpisode(abhaId: String, onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val episode = CareEpisode(patientAbhaId = abhaId, facilityName = "AAM-SHC Khed")
            val id = db.careEpisodeDao().insert(episode)
            _state.value = _state.value.copy(episodeId = id, abhaId = abhaId)
            onStarted(id)
        }
    }

    fun updateSymptoms(transcript: String, manualText: String, chips: List<String>) {
        _state.value = _state.value.copy(
            voiceTranscript = transcript,
            manualSymptoms = manualText,
            selectedSymptomChips = chips
        )
    }

    fun updateVitalsAndTests(
        bp: String, hr: Int, spo2: Int, temp: Float, bg: Int, hb: Float, wt: Float, ht: Float,
        pregnancy: String, malaria: String, dengue: String, urine: String, tb: String, notes: String
    ) {
        _state.value = _state.value.copy(
            bloodPressure = bp, heartRate = hr, spo2 = spo2, temperature = temp,
            bloodGlucose = bg, hemoglobin = hb, weight = wt, height = ht,
            pregnancyTest = pregnancy, malariaTest = malaria, dengueTest = dengue,
            urineTest = urine, tbScreening = tb, additionalNotes = notes
        )
    }

    fun updateTriageNotes(notes: String) {
        _state.value = _state.value.copy(clinicalNotes = notes)
    }

    fun saveFullEpisode(onSaved: () -> Unit) {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState.episodeId == 0L) return@launch

            db.symptomDao().insert(
                Symptom(
                    episodeId = currentState.episodeId,
                    voiceTranscript = currentState.voiceTranscript,
                    manualText = currentState.manualSymptoms,
                    selectedChips = currentState.selectedSymptomChips.joinToString(",")
                )
            )

            db.vitalsDao().insert(
                Vitals(
                    episodeId = currentState.episodeId,
                    bloodPressure = currentState.bloodPressure,
                    heartRate = currentState.heartRate,
                    spo2 = currentState.spo2,
                    temperature = currentState.temperature,
                    bloodGlucose = currentState.bloodGlucose,
                    hemoglobin = currentState.hemoglobin,
                    weight = currentState.weight,
                    height = currentState.height,
                    clinicalNotes = currentState.additionalNotes
                )
            )

            db.triageResultDao().insert(
                TriageResult(
                    episodeId = currentState.episodeId,
                    probableCondition = currentState.probableCondition,
                    severity = currentState.severity,
                    recommendedFacility = currentState.recommendedFacility,
                    recommendedProfessional = currentState.recommendedProfessional,
                    justification = currentState.justification,
                    clinicalNotes = currentState.clinicalNotes
                )
            )

            onSaved()
        }
    }
}
