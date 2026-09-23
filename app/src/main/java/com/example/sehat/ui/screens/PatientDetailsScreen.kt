package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.model.LabReportModel
import com.example.sehat.data.model.PatientDetailsState
import com.example.sehat.data.model.TimelineEvent
import com.example.sehat.ui.components.LanguageSelectionModal
import com.example.sehat.ui.components.PatientAvatar
import com.example.sehat.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PatientDetailsScreen(
    state: PatientDetailsState,
    onStartScreeningClick: (languageName: String, languageCode: String) -> Unit,
    onVoiceScreeningClick: () -> Unit = {},
    onBackClick: () -> Unit
) {
    // Tab Index: 0: First Screening (DEFAULT), 1: Patient Info, 2: Timeline, 3: Lab Report
    var selectedTab by remember(state.abhaId) { mutableIntStateOf(0) }
    var showLanguageModal by remember { mutableStateOf(false) }

    if (showLanguageModal) {
        LanguageSelectionModal(
            initialLanguage = "Marathi (मराठी)",
            onDismiss = { showLanguageModal = false },
            onStartScreening = { lang ->
                showLanguageModal = false
                onStartScreeningClick(lang.displayName, lang.id)
            }
        )
    }

    Scaffold(
        containerColor = CreamBackground,
        bottomBar = {
            Surface(
                color = CreamBackground,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = { showLanguageModal = true },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaroonPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Start New Screening",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Pinned Top Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaroonPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = if (selectedTab == 0) "First Screening Report" else "Patient Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaroonPrimary,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "Options",
                        tint = MaroonPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

            // 2. Fixed Top Patient Summary Card with Fake Avatar & Sync Indicator
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Illustrated Fake Avatar
                            PatientAvatar(
                                name = state.name,
                                gender = state.gender,
                                size = 52.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = state.name,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "ABHA ID: ${state.abhaId}",
                                    fontSize = 12.sp,
                                    color = TextMuted
                                )
                                Text(
                                    text = "${state.age} Yrs • ${state.gender} • ${state.village}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        // Sync Status Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (state.isReportSynced) Color(0xFFE8F5E9) else Color(0xFFF2F2F2)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = if (state.isReportSynced) "Synced" else "Not Synced",
                                    tint = if (state.isReportSynced) Color(0xFF2E7D32) else Color(0xFF9E9E9E),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (state.isReportSynced) "Synced" else "Local",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (state.isReportSynced) Color(0xFF2E7D32) else Color(0xFF757575)
                                )
                            }
                        }
                    }
                }
            }

            // 3. Tab Selector Row
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFF5EFE6),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        TabButton(
                            text = "Screening",
                            icon = Icons.Outlined.Description,
                            isSelected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            modifier = Modifier.weight(1f)
                        )
                        TabButton(
                            text = "Patient Info",
                            icon = Icons.Outlined.Person,
                            isSelected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            modifier = Modifier.weight(1f)
                        )
                        TabButton(
                            text = "Timeline",
                            icon = Icons.Outlined.Schedule,
                            isSelected = selectedTab == 2,
                            onClick = { selectedTab = 2 },
                            modifier = Modifier.weight(1f)
                        )
                        TabButton(
                            text = "Lab Reports",
                            icon = Icons.Outlined.Science,
                            isSelected = selectedTab == 3,
                            onClick = { selectedTab = 3 },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // 4. Dynamic Lower Content per Tab
            when (selectedTab) {
                0 -> {
                    // TAB 0: FIRST SCREENING REPORT (Full contents of initial screening)
                    val report = state.latestReport

                    // Header Banner with Sync & Screening Date
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(MaroonPrimary.copy(alpha = 0.1f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Description,
                                                contentDescription = null,
                                                tint = MaroonPrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = "First Screening Report",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaroonPrimary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = report?.date ?: state.lastScreeningDate,
                                                fontSize = 11.sp,
                                                color = TextMuted,
                                                maxLines = 1
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // Synced / Local tick pill
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (state.isReportSynced) Color(0xFFE8F5E9) else Color(0xFFF2F2F2)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = if (state.isReportSynced) Color(0xFF2E7D32) else Color(0xFF9E9E9E),
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = if (state.isReportSynced) "Synced" else "Local Only",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (state.isReportSynced) Color(0xFF2E7D32) else Color(0xFF757575)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Overall Assessment Banner
                                val isHigh = report?.overallRisk?.contains("High", ignoreCase = true) == true
                                val isModerate = report?.overallRisk?.contains("Moderate", ignoreCase = true) == true
                                val bannerBg = when {
                                    isHigh -> Color(0xFFFFEBEE)
                                    isModerate -> Color(0xFFFFF3E0)
                                    else -> Color(0xFFEFF7F0)
                                }
                                val bannerAccent = when {
                                    isHigh -> Color(0xFFC62828)
                                    isModerate -> Color(0xFFE65100)
                                    else -> Color(0xFF2E7D32)
                                }

                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = bannerBg,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(CircleShape)
                                                .background(bannerAccent.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MonitorHeart,
                                                contentDescription = null,
                                                tint = bannerAccent,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("Overall Assessment", fontSize = 11.sp, color = TextSecondary)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.overallRisk ?: "Low Risk",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = bannerAccent
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.riskDescription ?: "Initial health parameters within normal range.",
                                                fontSize = 11.sp,
                                                lineHeight = 15.sp,
                                                color = TextSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Vitals & Measurements Grid
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = null,
                                        tint = MaroonPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Recorded Vital Signs",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // 2-Column Vitals Grid
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    VitalBox("Blood Pressure", state.bp, Icons.Outlined.Speed, modifier = Modifier.weight(1f))
                                    VitalBox("Heart Rate", state.heartRate, Icons.Outlined.Favorite, modifier = Modifier.weight(1f))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    VitalBox("SpO₂", state.spO2, Icons.Outlined.Air, modifier = Modifier.weight(1f))
                                    VitalBox("Temperature", state.temperature, Icons.Outlined.Thermostat, modifier = Modifier.weight(1f))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    VitalBox("Blood Glucose", state.bloodGlucose, Icons.Outlined.WaterDrop, modifier = Modifier.weight(1f))
                                    VitalBox("Hemoglobin", state.hemoglobin, Icons.Outlined.Bloodtype, modifier = Modifier.weight(1f))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    VitalBox("Height", state.height, Icons.Outlined.Straighten, modifier = Modifier.weight(1f))
                                    VitalBox("Weight", state.weight, Icons.Outlined.FitnessCenter, modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                    // Primary Complaints & Clinical Diagnosis
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Primary Complaints
                                Text(
                                    text = "Chief Complaints & Symptoms",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val complaints = report?.primaryComplaints ?: listOf("Routine Checkup")
                                    complaints.forEach { complaint ->
                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = MaroonPrimary.copy(alpha = 0.1f)
                                        ) {
                                            Text(
                                                text = complaint,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaroonPrimary,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))
                                HorizontalDivider(color = Color(0xFFF0EBE1))
                                Spacer(modifier = Modifier.height(14.dp))

                                // Likely Diagnosis
                                Text(
                                    text = "Provisional Diagnosis",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = report?.likelyDiagnosis ?: "Healthy / Routine Screening",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaroonPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = report?.diagnosisNote ?: "All initial vitals and physical signs within standard range.",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    // Recommendations: Facility, Doctor, Appointment
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Referral & Recommendations",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Facility
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFFAF7F2),
                                        modifier = Modifier.weight(1f).height(112.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(Icons.Default.LocalHospital, contentDescription = null, tint = MaroonPrimary, modifier = Modifier.size(20.dp))
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("Facility", fontSize = 10.sp, color = TextSecondary, textAlign = TextAlign.Center)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.recommendedFacility ?: "PHC Khed",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextPrimary,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.facilityDistance ?: "2.5 km",
                                                fontSize = 9.sp,
                                                color = TextMuted,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1
                                            )
                                        }
                                    }

                                    // Doctor
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFFAF7F2),
                                        modifier = Modifier.weight(1f).height(112.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(Icons.Default.Person, contentDescription = null, tint = MaroonPrimary, modifier = Modifier.size(20.dp))
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("Doctor", fontSize = 10.sp, color = TextSecondary, textAlign = TextAlign.Center)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.recommendedDoctor ?: "Dr. Deshmukh",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextPrimary,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.doctorSpecialty ?: "General",
                                                fontSize = 9.sp,
                                                color = TextMuted,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1
                                            )
                                        }
                                    }

                                    // Appointment
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFFAF7F2),
                                        modifier = Modifier.weight(1f).height(112.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(Icons.Default.Event, contentDescription = null, tint = MaroonPrimary, modifier = Modifier.size(20.dp))
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text("Status", fontSize = 10.sp, color = TextSecondary, textAlign = TextAlign.Center)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = Color(0xFFE8F5E9)
                                            ) {
                                                Text(
                                                    text = report?.appointmentStatus ?: "Recorded",
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF2E7D32),
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = report?.appointmentTime ?: "Completed",
                                                fontSize = 8.sp,
                                                color = TextMuted,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Medications & Prescriptions
                    item {
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Medications & Instructions",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                DetailRow("Current Medications", state.currentMedications)
                                DetailRow("Known Allergies", state.knownAllergies)
                                DetailRow("Chronic Conditions", state.chronicConditions.joinToString(", "))
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: PATIENT INFO (Demographics & Medical Record)
                    item {
                        DetailSectionCard(
                            title = "Personal Details",
                            icon = Icons.Outlined.Person,
                            onEditClick = { }
                        ) {
                            DetailRow("Full Name", state.name)
                            DetailRow("ABHA ID", state.abhaId)
                            DetailRow("Mobile Number", state.mobile)
                            DetailRow("Aadhaar Linked", state.aadhaarLinked)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("ABHA Status", fontSize = 12.sp, color = TextSecondary)
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFE8F5E9)
                                ) {
                                    Text(
                                        text = state.abhaStatus,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        DetailSectionCard(
                            title = "Demographics",
                            icon = Icons.Outlined.Home,
                            onEditClick = { }
                        ) {
                            DetailRow("Age", "${state.age} Years")
                            DetailRow("Gender", state.gender)
                            DetailRow("Height", state.height)
                            DetailRow("Weight", state.weight)
                            DetailRow("Village", state.village)
                            DetailRow("District", state.district)
                            DetailRow("State", state.state)
                        }
                    }

                    item {
                        DetailSectionCard(
                            title = "Medical Information",
                            icon = Icons.Outlined.FavoriteBorder,
                            onEditClick = { }
                        ) {
                            DetailRow("Blood Group", state.bloodGroup)

                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Chronic Diseases", fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                state.chronicConditions.forEach { condition ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFF7EBEB)
                                    ) {
                                        Text(
                                            text = condition,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaroonPrimary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            DetailRow("Known Allergies", state.knownAllergies)
                            DetailRow("Current Medications", state.currentMedications)
                            DetailRow("Past Surgeries", state.pastSurgeries)
                            DetailRow("Other Notes", state.otherNotes)
                        }
                    }
                }

                2 -> {
                    // TAB 2: TIMELINE (Screen 3 Mockup)
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF7EBEB)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Schedule,
                                        contentDescription = null,
                                        tint = MaroonPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Timeline",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaroonPrimary
                                    )
                                    Text(
                                        text = "Complete history of care for this patient",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }

                    itemsIndexed(state.timeline) { index, event ->
                        TimelineListItem(
                            event = event,
                            isFirst = index == 0,
                            isLast = index == state.timeline.lastIndex
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("No more events", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text("This is the complete history available for this patient.", fontSize = 11.sp, color = TextMuted)
                        }
                    }
                }

                3 -> {
                    // TAB 3: LAB REPORTS
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF7EBEB)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Science,
                                        contentDescription = null,
                                        tint = MaroonPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Lab Reports",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaroonPrimary
                                    )
                                    Text(
                                        text = "All diagnostic test reports for this patient",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }

                    itemsIndexed(state.labReports) { _, report ->
                        LabReportListItem(report = report)
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("No more reports", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text("All available lab reports are shown above", fontSize = 11.sp, color = TextMuted)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
}

@Composable
private fun VitalBox(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFAF7F2),
        modifier = modifier.height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF7EBEB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaroonPrimary,
                    modifier = Modifier.size(17.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = label,
                    fontSize = 10.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = value,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun DetailSectionCard(
    title: String,
    icon: ImageVector,
    onEditClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF7EBEB)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = MaroonPrimary, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF7EBEB),
                    modifier = Modifier.clickable { onEditClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Edit, contentDescription = "Edit", tint = MaroonPrimary, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Edit", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.weight(0.42f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.58f)
        )
    }
}

@Composable
private fun TabButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) MaroonPrimary else Color.Transparent,
        modifier = modifier
            .height(38.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else TextPrimary,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = text,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else TextPrimary,
                maxLines = 1,
                letterSpacing = (-0.2).sp
            )
        }
    }
}

@Composable
private fun TimelineListItem(
    event: TimelineEvent,
    isFirst: Boolean,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 12.dp, top = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(if (isFirst) MaroonPrimary else Color(0xFF9E9E9E))
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(70.dp)
                        .background(Color(0xFFE0E0E0))
                )
            }
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.date,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                    Text(
                        text = event.time,
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = event.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = event.description,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = event.facility,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (event.statusText == "Referred") Color(0xFFE3F2FD) else Color(0xFFE8F5E9)
                    ) {
                        Text(
                            text = event.statusText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(event.statusColorHex),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LabReportListItem(report: LabReportModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        when (report.iconType) {
                            "blood" -> Color(0xFFF7EBEB)
                            "sugar" -> Color(0xFFEDE7F6)
                            "lipid" -> Color(0xFFFBE9E7)
                            "covid" -> Color(0xFFE3F2FD)
                            else -> Color(0xFFFFF8E1)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (report.iconType) {
                        "blood" -> Icons.Outlined.WaterDrop
                        "sugar" -> Icons.Outlined.Science
                        "lipid" -> Icons.Outlined.Favorite
                        "covid" -> Icons.Outlined.Coronavirus
                        else -> Icons.Outlined.LocalPharmacy
                    },
                    contentDescription = null,
                    tint = MaroonPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = report.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = report.category,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Text(
                    text = report.date,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(report.statusColorHex).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = report.statusText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(report.statusColorHex),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
