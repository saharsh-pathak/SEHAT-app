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
        val targetAbha = if (abhaId.isNotBlank()) abhaId else "1234 5678 9012"

        val mockTimeline = listOf(
            TimelineEvent(
                id = "1",
                date = "28 Aug 2025",
                time = "10:15 AM",
                title = "Screening Consultation",
                facility = "Sub Health Centre, Khed",
                description = "Cough, Fever • Low Risk",
                eventType = TimelineEventType.SCREENING,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "2",
                date = "12 Jul 2025",
                time = "02:30 PM",
                title = "Follow-up Visit",
                facility = "Sub Health Centre, Khed",
                description = "Blood Pressure Check",
                eventType = TimelineEventType.FOLLOW_UP,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "3",
                date = "03 Jun 2025",
                time = "11:00 AM",
                title = "Lab Tests",
                facility = "Primary Health Centre, Khed",
                description = "Blood Sugar, CBC",
                eventType = TimelineEventType.DIAGNOSTICS,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "4",
                date = "15 Mar 2025",
                time = "01:20 PM",
                title = "Medication Review",
                facility = "Sub Health Centre, Khed",
                description = "Routine Follow-up",
                eventType = TimelineEventType.FOLLOW_UP,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "5",
                date = "20 Jan 2025",
                time = "09:45 AM",
                title = "Referred to PHC",
                facility = "Sub Health Centre, Khed",
                description = "For further evaluation",
                eventType = TimelineEventType.REFERRAL,
                statusText = "Referred",
                statusColorHex = 0xFF1565C0
            ),
            TimelineEvent(
                id = "6",
                date = "05 Dec 2024",
                time = "11:30 AM",
                title = "General Consultation",
                facility = "Sub Health Centre, Khed",
                description = "Headache, Fatigue",
                eventType = TimelineEventType.PHC_CONSULTATION,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "7",
                date = "10 Sep 2024",
                time = "04:10 PM",
                title = "Immunization",
                facility = "Sub Health Centre, Khed",
                description = "Routine Vaccination",
                eventType = TimelineEventType.IMMUNIZATION,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            ),
            TimelineEvent(
                id = "8",
                date = "18 Jun 2024",
                time = "10:00 AM",
                title = "Registration",
                facility = "Sub Health Centre, Khed",
                description = "Patient registered",
                eventType = TimelineEventType.REGISTRATION,
                statusText = "Completed",
                statusColorHex = 0xFF2E7D32
            )
        )

        val mockLabReports = listOf(
            LabReportModel(
                id = "r1",
                title = "Blood Test (CBC)",
                category = "Complete Blood Count",
                date = "28 Aug 2025",
                statusText = "Normal",
                statusColorHex = 0xFF2E7D32,
                iconType = "blood"
            ),
            LabReportModel(
                id = "r2",
                title = "Blood Sugar (RBS)",
                category = "Random Blood Sugar",
                date = "28 Aug 2025",
                statusText = "Normal",
                statusColorHex = 0xFF2E7D32,
                iconType = "sugar"
            ),
            LabReportModel(
                id = "r3",
                title = "Lipid Profile",
                category = "Cholesterol, HDL, LDL, Triglycerides",
                date = "15 Jun 2025",
                statusText = "High",
                statusColorHex = 0xFFC62828,
                iconType = "lipid"
            ),
            LabReportModel(
                id = "r4",
                title = "COVID-19 Test",
                category = "RT-PCR",
                date = "03 Feb 2025",
                statusText = "Negative",
                statusColorHex = 0xFF2E7D32,
                iconType = "covid"
            ),
            LabReportModel(
                id = "r5",
                title = "Urine Routine",
                category = "Urine Analysis",
                date = "12 Jan 2025",
                statusText = "Normal",
                statusColorHex = 0xFF2E7D32,
                iconType = "urine"
            )
        )

        val isRamesh = targetAbha.contains("9876")
        val isLata = targetAbha.contains("1111")
        val isSaharsh = targetAbha.contains("4983")
        val isTukaram = targetAbha.contains("5566") || targetAbha.contains("7788")
        val isSynced = !isRamesh && !isSaharsh && !isTukaram

        val patientName = when {
            isRamesh -> "Ramesh Pawar"
            isTukaram -> "Tukaram Shinde"
            isLata -> "Lata Shinde"
            isSaharsh -> "SAHARSH"
            else -> "Sita Devi"
        }

        val patientAge = when {
            isRamesh -> 52
            isTukaram -> 68
            isLata -> 28
            isSaharsh -> 19
            else -> 34
        }

        val patientGender = when {
            isRamesh || isSaharsh || isTukaram -> "Male"
            else -> "Female"
        }

        val patientVillage = when {
            isLata -> "Nandgaon"
            else -> "Khed"
        }

        val firstScreeningReport = when {
            isRamesh -> ScreeningReportModel(
                date = "20 Aug 2025 • 09:30 AM",
                overallRisk = "High Risk",
                riskDescription = "Severe hypertension (160/100 mmHg) with recurring dizziness & chest pain. Emergency referral required.",
                primaryComplaints = listOf("Severe Headache", "Chest Pain", "Dizziness"),
                likelyDiagnosis = "Stage 2 Essential Hypertension",
                diagnosisNote = "Urgent consultation and stabilization advised at PHC.",
                recommendedFacility = "PHC Khed",
                facilityDistance = "2.5 km away",
                recommendedDoctor = "Dr. Rajesh Kulkarni",
                doctorSpecialty = "Cardiology / Internal Medicine",
                appointmentStatus = "Urgent Referral",
                appointmentTime = "Immediate"
            )
            isTukaram -> ScreeningReportModel(
                date = "22 Aug 2025 • 08:15 AM",
                overallRisk = "High Risk",
                riskDescription = "Low oxygen saturation (SpO2 88%) with acute breathlessness and chest tightness. Emergency care required.",
                primaryComplaints = listOf("Breathlessness", "Chest Tightness", "Wheezing"),
                likelyDiagnosis = "Acute Respiratory Distress / Severe COPD",
                diagnosisNote = "Immediate oxygen therapy and emergency transfer advised.",
                recommendedFacility = "PHC Khed / District Hospital",
                facilityDistance = "2.5 km away",
                recommendedDoctor = "Dr. Rajesh Kulkarni",
                doctorSpecialty = "Pulmonology / Critical Care",
                appointmentStatus = "Emergency Referral",
                appointmentTime = "Immediate"
            )
            isLata -> ScreeningReportModel(
                date = "15 Aug 2025 • 11:15 AM",
                overallRisk = "Low Risk (ANC)",
                riskDescription = "Routine 2nd trimester antenatal checkup. Healthy fetal vitals.",
                primaryComplaints = listOf("Mild Nausea", "Backache"),
                likelyDiagnosis = "Normal Intrauterine Pregnancy (22 Wks)",
                diagnosisNote = "IFA tablets & Calcium distributed. Next visit in 4 weeks.",
                recommendedFacility = "Sub-Centre Nandgaon",
                facilityDistance = "1.2 km away",
                recommendedDoctor = "Dr. Sneha Patil",
                doctorSpecialty = "Obstetrics & Gynecology",
                appointmentStatus = "Completed",
                appointmentTime = "11:00 AM, Next Visit"
            )
            isSaharsh -> ScreeningReportModel(
                date = "10 Aug 2025 • 03:00 PM",
                overallRisk = "Low Risk",
                riskDescription = "Routine village health camp youth screening. Excellent health parameters.",
                primaryComplaints = listOf("None", "Routine Checkup"),
                likelyDiagnosis = "Healthy Individual",
                diagnosisNote = "All vital parameters within optimal physiological range.",
                recommendedFacility = "PHC Khed",
                facilityDistance = "2.5 km away",
                recommendedDoctor = "Dr. Anita Deshmukh",
                doctorSpecialty = "General Medicine",
                appointmentStatus = "Recorded",
                appointmentTime = "Annual Review"
            )
            else -> ScreeningReportModel(
                date = "28 Aug 2025 • 10:15 AM",
                overallRisk = "Low Risk",
                riskDescription = "Mild upper respiratory tract symptoms. Vitals stable. Good prognosis.",
                primaryComplaints = listOf("Cough (3 days)", "Mild Fever", "Body Ache"),
                likelyDiagnosis = "Acute Viral Bronchitis",
                diagnosisNote = "Symptomatic treatment with Paracetamol & Cough Syrup. Hydration advised.",
                recommendedFacility = "PHC Khed",
                facilityDistance = "2.5 km away",
                recommendedDoctor = "Dr. Anita Deshmukh",
                doctorSpecialty = "General Medicine",
                appointmentStatus = "Auto-Confirmed",
                appointmentTime = "10:30 AM, Tomorrow"
            )
        }

        _patientState.value = PatientDetailsState(
            abhaId = targetAbha,
            name = patientName,
            age = patientAge,
            gender = patientGender,
            village = patientVillage,
            district = "Pune",
            state = "Maharashtra",
            mobile = if (isRamesh) "9876 5432 00" else "9876 5432 10",
            aadhaarLinked = "Yes",
            abhaStatus = "Verified",
            height = if (isRamesh) "172 cm" else "158 cm",
            weight = if (isRamesh) "78 kg" else "62 kg",
            bloodGroup = if (isRamesh) "O+" else "B+",
            bp = if (isRamesh) "142/92 mmHg" else if (isLata) "115/75 mmHg" else "120/80 mmHg",
            heartRate = if (isRamesh) "82 bpm" else if (isLata) "78 bpm" else "74 bpm",
            spO2 = if (isRamesh) "97%" else if (isLata) "99%" else "98%",
            temperature = if (isRamesh) "98.4 °F" else "98.6 °F",
            bloodGlucose = if (isRamesh) "135 mg/dL" else if (isLata) "95 mg/dL" else "105 mg/dL",
            hemoglobin = if (isRamesh) "13.8 g/dL" else if (isLata) "11.4 g/dL" else "12.2 g/dL",
            isReportSynced = isSynced,
            chronicConditions = if (isRamesh) listOf("Hypertension", "Diabetes") else listOf("Hypertension", "Diabetes", "Asthma", "Thyroid"),
            knownAllergies = "No known allergies",
            currentMedications = if (isRamesh) "Amlodipine 10 mg (daily)" else "Amlodipine 5 mg (daily), Metformin 500 mg (daily)",
            pastSurgeries = "None",
            otherNotes = "N/A",
            healthStatus = if (isRamesh) "Needs Attention" else "Good",
            lastScreeningDate = firstScreeningReport.date.substringBefore(" •"),
            activeCareEpisode = if (isRamesh) "Active (PHC Referral)" else "Follow-up Completed",
            latestReport = firstScreeningReport,
            timeline = mockTimeline,
            labReports = mockLabReports
        )
    }
}
