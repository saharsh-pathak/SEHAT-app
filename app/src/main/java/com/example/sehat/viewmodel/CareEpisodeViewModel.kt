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
import java.util.UUID

data class DynamicTestModel(
    val id: String = UUID.randomUUID().toString(),
    val testName: String,
    val result: String,
    val unit: String = "",
    val remark: String = ""
)

data class CareEpisodeState(
    val episodeId: Long = 0,
    val abhaId: String = "9876 5432 1098",
    val patientName: String = "Ramesh Pawar",
    val patientAge: Int = 52,
    val patientGender: String = "Male",
    val patientMobile: String = "9876 5432 00",
    val patientVillage: String = "Khed, Pune",
    val selectedLanguage: String = "Marathi (मराठी)",
    val languageCode: String = "mr",
    val voiceTranscript: String = "मला दोन दिवसांपासून खूप ताप आणि तीव्र खोकला येत आहे. छातीत जडपणा आणि चक्कर जाणवते.",
    val manualSymptoms: String = "",
    val selectedSymptomChips: List<String> = listOf("Fever", "Cough", "Chest Pain", "Dizziness", "Breathlessness"),
    val bloodPressure: String = "160/100",
    val heartRate: Int = 94,
    val spo2: Int = 92,
    val temperature: Float = 38.6f,
    val bloodGlucose: Int = 138,
    val hemoglobin: Float = 12.4f,
    val weight: Float = 76.0f,
    val height: Float = 172.0f,
    val dynamicTests: List<DynamicTestModel> = listOf(
        DynamicTestModel(testName = "Blood Glucose (RBS)", result = "138", unit = "mg/dL", remark = "Post-meal sample"),
        DynamicTestModel(testName = "Hemoglobin", result = "12.4", unit = "g/dL", remark = "Within normal limit"),
        DynamicTestModel(testName = "Malaria RDT", result = "Negative", unit = "", remark = "Rapid antigen card"),
        DynamicTestModel(testName = "Dengue NS1", result = "Negative", unit = "", remark = "Rapid cassette")
    ),
    val clinicalObservations: String = "Patient presented with severe fatigue, diaphoresis, and acute dizziness upon standing. Auscultation reveals bilateral coarse crepitations in lower lobes. Peripheral pulses bounding, heart sounds normal with tachycardia.",
    val probableCondition: String = "Stage 2 Hypertension with Acute Respiratory Distress",
    val severity: String = "High", // Low, Medium, High, Emergency
    val justification: String = "Systolic BP 160 mmHg and Diastolic BP 100 mmHg with SpO₂ 92%, combined with chest heaviness, severe dizziness, and crepitations warrants urgent clinical evaluation at Sub-District Hospital.",
    val recommendedFacility: String = "Sub-District Hospital (SDH) Chakan",
    val recommendedProfessional: String = "Cardiologist / MD Physician",
    val recommendedDepartment: String = "Cardiology & Emergency Care",
    val autoAppointmentDate: String = "Tomorrow, 10:30 AM",
    val autoAppointmentToken: String = "Token #04",
    val generatedReferralId: String = "REF-2025-089"
)

class CareEpisodeViewModel(application: Application) : AndroidViewModel(application) {
    private val db = SehatDatabase.get(application)

    private val _state = MutableStateFlow(CareEpisodeState())
    val state: StateFlow<CareEpisodeState> = _state.asStateFlow()

    fun setLanguage(displayName: String, code: String) {
        _state.value = _state.value.copy(
            selectedLanguage = displayName,
            languageCode = code
        )
    }

    fun startEpisode(abhaId: String, onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val patient = db.patientDao().getByAbhaId(abhaId)
            val episode = CareEpisode(patientAbhaId = abhaId, facilityName = "AAM-SHC Khed")
            val id = db.careEpisodeDao().insert(episode)

            _state.value = _state.value.copy(
                episodeId = id,
                abhaId = abhaId,
                patientName = patient?.name ?: _state.value.patientName,
                patientAge = patient?.age ?: _state.value.patientAge,
                patientGender = patient?.gender ?: _state.value.patientGender,
                patientMobile = patient?.mobile ?: _state.value.patientMobile,
                patientVillage = patient?.village ?: _state.value.patientVillage
            )
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

    fun addSymptom(symptom: String) {
        val current = _state.value.selectedSymptomChips.toMutableList()
        if (!current.contains(symptom)) {
            current.add(symptom)
            _state.value = _state.value.copy(selectedSymptomChips = current)
        }
    }

    fun removeSymptom(symptom: String) {
        val current = _state.value.selectedSymptomChips.toMutableList()
        current.remove(symptom)
        _state.value = _state.value.copy(selectedSymptomChips = current)
    }

    fun updateVitals(
        bp: String, hr: Int, spo2: Int, temp: Float,
        wt: Float, ht: Float
    ) {
        _state.value = _state.value.copy(
            bloodPressure = bp,
            heartRate = hr,
            spo2 = spo2,
            temperature = temp,
            weight = wt,
            height = ht
        )
    }

    fun updateObservations(obs: String) {
        _state.value = _state.value.copy(clinicalObservations = obs)
    }

    fun addDynamicTest(name: String, result: String, unit: String, remark: String) {
        val list = _state.value.dynamicTests.toMutableList()
        list.add(DynamicTestModel(testName = name, result = result, unit = unit, remark = remark))
        _state.value = _state.value.copy(dynamicTests = list)
    }

    fun editDynamicTest(id: String, name: String, result: String, unit: String, remark: String) {
        val list = _state.value.dynamicTests.map {
            if (it.id == id) it.copy(testName = name, result = result, unit = unit, remark = remark) else it
        }
        _state.value = _state.value.copy(dynamicTests = list)
    }

    fun deleteDynamicTest(id: String) {
        val list = _state.value.dynamicTests.filterNot { it.id == id }
        _state.value = _state.value.copy(dynamicTests = list)
    }

    fun saveFullEpisode(onSaved: () -> Unit) {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState.episodeId != 0L) {
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
                        clinicalNotes = currentState.clinicalObservations
                    )
                )

                // Save dynamic tests
                val testsToInsert = currentState.dynamicTests.map { test ->
                    ScreeningTest(
                        episodeId = currentState.episodeId,
                        testName = test.testName,
                        result = if (test.unit.isNotBlank()) "${test.result} ${test.unit}" else test.result
                    )
                }
                if (testsToInsert.isNotEmpty()) {
                    db.screeningTestDao().insertAll(testsToInsert)
                }

                db.triageResultDao().insert(
                    TriageResult(
                        episodeId = currentState.episodeId,
                        probableCondition = currentState.probableCondition,
                        severity = currentState.severity,
                        recommendedFacility = currentState.recommendedFacility,
                        recommendedProfessional = currentState.recommendedProfessional,
                        justification = currentState.justification,
                        clinicalNotes = currentState.clinicalObservations
                    )
                )

                val refId = db.referralDao().insert(
                    Referral(
                        episodeId = currentState.episodeId,
                        patientAbhaId = currentState.abhaId,
                        destinationFacility = currentState.recommendedFacility,
                        priority = "Urgent",
                        notes = currentState.probableCondition
                    )
                )

                db.appointmentDao().insert(
                    Appointment(
                        appointmentCode = currentState.generatedReferralId,
                        referralId = refId,
                        patientAbhaId = currentState.abhaId,
                        facility = currentState.recommendedFacility,
                        dateTime = currentState.autoAppointmentDate,
                        queueToken = currentState.autoAppointmentToken
                    )
                )
            }
            onSaved()
        }
    }
}
