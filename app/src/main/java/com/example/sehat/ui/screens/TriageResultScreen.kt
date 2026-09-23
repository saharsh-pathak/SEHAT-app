package com.example.sehat.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sehat.ui.components.PatientAvatar
import com.example.sehat.ui.theme.*
import com.example.sehat.viewmodel.CareEpisodeState
import com.example.sehat.viewmodel.DynamicTestModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TriageResultScreen(
    state: CareEpisodeState,
    onSaveDraft: () -> Unit = {},
    onSubmitScreening: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    // Editable state holders
    var symptomsList = remember { mutableStateListOf<String>().apply { addAll(state.selectedSymptomChips) } }
    var observationsText by remember { mutableStateOf(state.clinicalObservations) }
    var bpText by remember { mutableStateOf(state.bloodPressure) }
    var hrText by remember { mutableStateOf(state.heartRate.toString()) }
    var spo2Text by remember { mutableStateOf(state.spo2.toString()) }
    var tempText by remember { mutableStateOf(state.temperature.toString()) }
    val dynamicTestsList = remember { mutableStateListOf<DynamicTestModel>().apply { addAll(state.dynamicTests) } }

    var showAddSymptomDialog by remember { mutableStateOf(false) }
    var newSymptomInput by remember { mutableStateOf("") }

    if (showAddSymptomDialog) {
        Dialog(onDismissRequest = { showAddSymptomDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Add Symptom", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = newSymptomInput,
                        onValueChange = { newSymptomInput = it },
                        label = { Text("Symptom name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showAddSymptomDialog = false }) {
                            Text("Cancel", color = TextSecondary)
                        }
                        Button(
                            onClick = {
                                if (newSymptomInput.isNotBlank()) {
                                    symptomsList.add(newSymptomInput.trim())
                                    newSymptomInput = ""
                                    showAddSymptomDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = CreamBackground,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaroonPrimary
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Screening Summary",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Text(
                            text = "Step 3 of 4 • Clinical Review & Assessment",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = CreamBackground,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onSaveDraft()
                            Toast.makeText(context, "Draft saved locally", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaroonPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonPrimary),
                        modifier = Modifier
                            .weight(0.4f)
                            .height(52.dp)
                    ) {
                        Icon(
                            Icons.Outlined.BookmarkBorder, 
                            contentDescription = null, 
                            tint = MaroonPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Save Draft", 
                            fontSize = 14.sp, 
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                    }

                    Button(
                        onClick = onSubmitScreening,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaroonPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(0.6f)
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Submit Screening",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        },
        containerColor = CreamBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // 1. Patient Summary Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PatientAvatar(
                            name = state.patientName,
                            gender = state.patientGender,
                            size = 54.dp
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = state.patientName,
                                fontSize = 16.sp,
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
                                text = "${state.patientAge} Yrs • ${state.patientGender} • ${state.patientVillage}",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "Mobile: ${state.patientMobile}",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            // 2. Symptoms Captured (Editable)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            Text(
                                text = "Symptoms Captured",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaroonPrimary
                            )

                            IconButton(
                                onClick = { showAddSymptomDialog = true },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircleOutline,
                                    contentDescription = "Add Symptom",
                                    tint = MaroonPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            symptomsList.forEach { symptom ->
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFFBEBEB),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaroonPrimary.copy(alpha = 0.3f))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(start = 10.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = symptom,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaroonPrimary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = MaroonPrimary,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .clickable { symptomsList.remove(symptom) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 3. Key Vitals Summary (Editable)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            text = "Key Vitals Recorded",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            VitalSummaryPill("BP", bpText, "mmHg", isCritical = true, modifier = Modifier.weight(1f))
                            VitalSummaryPill("Heart Rate", hrText, "bpm", isCritical = false, modifier = Modifier.weight(1f))
                            VitalSummaryPill("SpO₂", spo2Text, "%", isCritical = true, modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            VitalSummaryPill("Temp", tempText, "°C", isCritical = true, modifier = Modifier.weight(1f))
                            VitalSummaryPill("Height", "${state.height.toInt()}", "cm", isCritical = false, modifier = Modifier.weight(1f))
                            VitalSummaryPill("Weight", "${state.weight.toInt()}", "kg", isCritical = false, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // 4. Dynamic Tests Conducted
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            text = "Tests Conducted (${dynamicTestsList.size})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        dynamicTestsList.forEach { test ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = test.testName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                                Text(
                                    text = if (test.unit.isNotBlank()) "${test.result} ${test.unit}" else test.result,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaroonPrimary
                                )
                            }
                            HorizontalDivider(color = Color(0xFFF0EAE1), thickness = 0.5.dp)
                        }
                    }
                }
            }

            // 5. Clinical Observations (Editable)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            text = "Clinical Observations",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (observationsText.isBlank()) "No clinical observations entered." else observationsText,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            fontStyle = if (observationsText.isBlank()) androidx.compose.ui.text.font.FontStyle.Italic else androidx.compose.ui.text.font.FontStyle.Normal,
                            color = if (observationsText.isBlank()) TextMuted else TextPrimary
                        )
                    }
                }
            }

            // 6. CARD TITLE: Severity Assessment (STRICTLY NOT AI Risk)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFC5221F)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Severity Assessment",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC5221F)
                            )

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFC5221F)
                            ) {
                                Text(
                                    text = state.severity.uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Probable Clinical Condition",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF8B1E1E)
                        )
                        Text(
                            text = state.probableCondition,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Clinical Justification",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF8B1E1E)
                        )
                        Text(
                            text = state.justification,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = Color(0xFF3E2723)
                        )
                    }
                }
            }

            // 7. Recommended Care Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            text = "Recommended Care",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CareInfoRow(label = "Healthcare Professional", value = state.recommendedProfessional)
                        CareInfoRow(label = "Destination Facility", value = state.recommendedFacility)
                        CareInfoRow(label = "Department", value = state.recommendedDepartment)
                    }
                }
            }

            // 8. Appointment Queue Information
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFFF8E1),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFE082)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.EventAvailable,
                                contentDescription = null,
                                tint = Color(0xFFF57F17),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Auto Appointment Queue",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Appointment request is automatically generated and queued at the selected healthcare facility after screening submission.",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = Color(0xFF5D4037)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Scheduled: ${state.autoAppointmentDate}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = state.autoAppointmentToken,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaroonPrimary
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
        }
    }
}

@Composable
private fun VitalSummaryPill(
    label: String,
    value: String,
    unit: String,
    isCritical: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isCritical) Color(0xFFFFEBEE) else Color(0xFFFAF7F2),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCritical) Color(0xFFC5221F).copy(alpha = 0.5f) else Color(0xFFE8E0D5)
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 10.sp, color = if (isCritical) Color(0xFFC5221F) else TextSecondary)
            Text(
                text = "$value $unit",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCritical) Color(0xFFC5221F) else TextPrimary
            )
        }
    }
}

@Composable
private fun CareInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
    }
}
