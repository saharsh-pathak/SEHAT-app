package com.example.sehat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sehat.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PatientDetailsViewModel : ViewModel() {

    private val _patientState = MutableStateFlow(PatientDetailsState())
    val patientState: StateFlow<PatientDetailsState> = _patientState.asStateFlow()

    fun loadPatientDetails(abhaId: String) {
        val mockReport = ScreeningReportModel(
            date = "28 Aug 2026",
            overallRisk = "Low Risk",
            riskDescription = "No immediate danger signs detected.",
            primaryComplaints = listOf("Cough", "Fever"),
            likelyDiagnosis = "Acute Respiratory Infection",
            diagnosisNote = "(Likely Viral)",
            recommendedFacility = "Sub Health Centre",
            facilityDistance = "Khed (2.3 km)",
            recommendedDoctor = "General Physician",
            doctorSpecialty = "(MBBS)",
            appointmentStatus = "Booked",
            appointmentTime = "28 Aug 2026, 11:30 AM"
        )

        val mockTimeline = listOf(
            TimelineEvent(
                id = "1",
                date = "05 Sep 2026",
                time = "11:00 AM",
                title = "Recovery Verified",
                facility = "Village Visit • Khed",
                description = "Patient reported complete resolution of symptoms.",
                eventType = TimelineEventType.FOLLOW_UP,
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "2",
                date = "02 Sep 2026",
                time = "02:30 PM",
                title = "Follow-up Home Visit",
                facility = "Home Visit • Khed",
                description = "Medication adherence checked. Vital signs normal.",
                eventType = TimelineEventType.FOLLOW_UP,
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "3",
                date = "29 Aug 2026",
                time = "10:00 AM",
                title = "PHC Consultation Completed",
                facility = "Khed PHC",
                description = "Doctor prescribed 5-day course of Paracetamol & Syrup.",
                eventType = TimelineEventType.PHC_CONSULTATION,
                statusColorHex = 0xFF852A2A
            ),
            TimelineEvent(
                id = "4",
                date = "28 Aug 2026",
                time = "01:30 PM",
                title = "PHC Referral Created",
                facility = "Sub Health Centre Khed",
                description = "Referred to Medical Officer for persistent cough evaluation.",
                eventType = TimelineEventType.REFERRAL,
                statusColorHex = 0xFFD97706
            ),
            TimelineEvent(
                id = "5",
                date = "28 Aug 2026",
                time = "11:15 AM",
                title = "Blood Sugar + BP Test",
                facility = "AAM-SHC Khed",
                description = "BP: 124/82 mmHg • Random Blood Glucose: 110 mg/dL",
                eventType = TimelineEventType.DIAGNOSTICS,
                statusColorHex = 0xFF2563EB
            ),
            TimelineEvent(
                id = "6",
                date = "28 Aug 2026",
                time = "10:45 AM",
                title = "AAM-SHC Consultation",
                facility = "AAM-SHC Khed",
                description = "Preliminary examination by ANM. Vitals recorded.",
                eventType = TimelineEventType.PHC_CONSULTATION,
                statusColorHex = 0xFF852A2A
            ),
            TimelineEvent(
                id = "7",
                date = "28 Aug 2026",
                time = "09:30 AM",
                title = "Home Screening Completed",
                facility = "Home Visit • Khed",
                description = "Initial screening by Medical Worker. Symptoms: Cough, Fever.",
                eventType = TimelineEventType.SCREENING,
                statusColorHex = 0xFF852A2A
            )
        )

        val mockFollowUp = FollowUpSummaryModel(
            dueDate = "10 Sep 2026",
            assignedWorker = "Sunita Tai (Medical Worker)",
            homeVisitStatus = "Completed (02 Sep 2026)",
            medicationAdherence = "Good (100%)",
            recoveryStatus = "Recovered"
        )

        _patientState.value = PatientDetailsState(
            abhaId = if (abhaId.isNotBlank()) abhaId else "1234 5678 9012",
            name = if (abhaId.contains("9876")) "Ramesh Pawar" else "Sita Devi",
            age = if (abhaId.contains("9876")) 52 else 34,
            gender = if (abhaId.contains("9876")) "Male" else "Female",
            village = "Khed",
            mobile = "+91 98765 43210",
            healthStatus = "Low Risk",
            lastScreeningDate = "28 Aug 2026",
            activeCareEpisode = "Follow-up Completed",
            chronicConditions = listOf("Hypertension", "Diabetes", "Pregnancy", "TB"),
            latestReport = mockReport,
            timeline = mockTimeline,
            followUpSummary = mockFollowUp
        )
    }
}
