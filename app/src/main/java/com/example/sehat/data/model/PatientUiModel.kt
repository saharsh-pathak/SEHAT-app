package com.example.sehat.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TimelineEvent(
    val id: String,
    val date: String,
    val time: String,
    val title: String,
    val facility: String,
    val description: String,
    val eventType: TimelineEventType,
    val statusColorHex: Long = 0xFF852A2A
)

enum class TimelineEventType {
    SCREENING,
    PHC_CONSULTATION,
    FOLLOW_UP,
    DIAGNOSTICS,
    REFERRAL,
    HOSPITAL_ADMISSION,
    DISCHARGE
}

data class ScreeningReportModel(
    val date: String,
    val overallRisk: String, // Low Risk, Moderate Risk, High Risk
    val riskDescription: String,
    val primaryComplaints: List<String>,
    val likelyDiagnosis: String,
    val diagnosisNote: String,
    val recommendedFacility: String,
    val facilityDistance: String,
    val recommendedDoctor: String,
    val doctorSpecialty: String,
    val appointmentStatus: String,
    val appointmentTime: String
)

data class FollowUpSummaryModel(
    val dueDate: String,
    val assignedWorker: String,
    val homeVisitStatus: String,
    val medicationAdherence: String,
    val recoveryStatus: String
)

data class PatientDetailsState(
    val abhaId: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val village: String = "",
    val mobile: String = "",
    val healthStatus: String = "Good",
    val lastScreeningDate: String = "28 Aug 2026",
    val activeCareEpisode: String = "Active (PHC Referral)",
    val chronicConditions: List<String> = listOf("Hypertension", "Diabetes"),
    val latestReport: ScreeningReportModel? = null,
    val timeline: List<TimelineEvent> = emptyList(),
    val followUpSummary: FollowUpSummaryModel? = null
)
