package com.example.sehat.data.model

data class TimelineEvent(
    val id: String,
    val date: String,
    val time: String,
    val title: String,
    val facility: String,
    val description: String,
    val eventType: TimelineEventType,
    val statusText: String = "Completed",
    val statusColorHex: Long = 0xFF4CAF50
)

enum class TimelineEventType {
    SCREENING,
    PHC_CONSULTATION,
    FOLLOW_UP,
    DIAGNOSTICS,
    REFERRAL,
    HOSPITAL_ADMISSION,
    DISCHARGE,
    REGISTRATION,
    IMMUNIZATION
}

data class LabReportModel(
    val id: String,
    val title: String,
    val category: String,
    val date: String,
    val statusText: String, // Normal, High, Low, Positive, Negative
    val statusColorHex: Long = 0xFF4CAF50,
    val iconType: String = "blood" // blood, sugar, lipid, covid, urine
)

data class ScreeningReportModel(
    val date: String,
    val overallRisk: String,
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
    val abhaId: String = "1234 5678 9012",
    val name: String = "Sita Devi",
    val age: Int = 34,
    val gender: String = "Female",
    val village: String = "Khed",
    val district: String = "Pune",
    val state: String = "Maharashtra",
    val mobile: String = "9876 5432 10",
    val aadhaarLinked: String = "Yes",
    val abhaStatus: String = "Verified",
    val height: String = "158 cm",
    val weight: String = "62 kg",
    val bloodGroup: String = "B+",
    val bp: String = "120/80 mmHg",
    val heartRate: String = "74 bpm",
    val spO2: String = "98%",
    val temperature: String = "98.6 °F",
    val bloodGlucose: String = "105 mg/dL",
    val hemoglobin: String = "12.2 g/dL",
    val isReportSynced: Boolean = true,
    val chronicConditions: List<String> = listOf("Hypertension", "Diabetes", "Asthma", "Thyroid"),
    val knownAllergies: String = "No known allergies",
    val currentMedications: String = "Amlodipine 5 mg (daily), Metformin 500 mg (daily)",
    val pastSurgeries: String = "None",
    val otherNotes: String = "N/A",
    val healthStatus: String = "Good",
    val lastScreeningDate: String = "28 Aug 2025",
    val activeCareEpisode: String = "Active (PHC Referral)",
    val latestReport: ScreeningReportModel? = null,
    val timeline: List<TimelineEvent> = emptyList(),
    val labReports: List<LabReportModel> = emptyList(),
    val followUpSummary: FollowUpSummaryModel? = null
)
