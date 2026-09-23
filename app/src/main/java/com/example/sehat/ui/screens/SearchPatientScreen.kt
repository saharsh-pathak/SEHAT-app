package com.example.sehat.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Patient
import com.example.sehat.ui.components.PatientCard
import com.example.sehat.ui.theme.*

@Composable
fun SearchPatientScreen(
    searchQuery: String,
    patients: List<Patient>,
    isEmergencyReferral: Boolean = false,
    onQueryChange: (String) -> Unit,
    onPatientSelect: (Patient) -> Unit,
    onCreateAbha: (name: String, age: Int, gender: String, village: String, aadhaar: String, mobile: String) -> Unit,
    onBackClick: () -> Unit
) {
    val allDefaultPatients = listOf(
        Patient(abhaId = "1234 5678 9012", name = "Sita Devi", age = 34, gender = "Female", village = "Khed", mobile = "9876543210"),
        Patient(abhaId = "9876 5432 1098", name = "Ramesh Pawar", age = 52, gender = "Male", village = "Khed", mobile = "9876543200"),
        Patient(abhaId = "1111 2222 3333", name = "Lata Shinde", age = 28, gender = "Female", village = "Nandgaon", mobile = "9876543211"),
        Patient(abhaId = "5566 7788 9900", name = "Tukaram Shinde", age = 68, gender = "Male", village = "Khed", mobile = "9823456789"),
        Patient(abhaId = "9877 5658 4983", name = "SAHARSH", age = 19, gender = "Male", village = "Khed", mobile = "9877565849")
    )

    val highRiskAbhaIds = setOf("9876 5432 1098", "5566 7788 9900")

    val basePatients = if (patients.isNotEmpty()) patients else allDefaultPatients

    val displayPatients = if (isEmergencyReferral) {
        val matched = basePatients.filter {
            highRiskAbhaIds.contains(it.abhaId) || it.name.contains("Ramesh", ignoreCase = true) || it.name.contains("Tukaram", ignoreCase = true)
        }
        if (matched.any { it.name.contains("Tukaram", ignoreCase = true) }) {
            matched
        } else {
            matched + allDefaultPatients[3]
        }
    } else {
        basePatients
    }

    val filteredPatients = remember(displayPatients, searchQuery) {
        if (searchQuery.isBlank()) displayPatients
        else displayPatients.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.abhaId.contains(searchQuery, ignoreCase = true) ||
            it.village.contains(searchQuery, ignoreCase = true) ||
            (it.mobile?.contains(searchQuery, ignoreCase = true) == true)
        }
    }

    Scaffold(
        containerColor = CreamBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Top Header Bar: Back Button + Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaroonPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEmergencyReferral) "Emergency Referral" else "Patient History",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Minimized Simple Search Box (in place of gray "Timeline" title)
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaroonPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search for Patient via ABHA ID / MOBILE NO. / NAME",
                                    fontSize = 11.sp,
                                    color = Color(0xFF8A827A),
                                    maxLines = 1
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = onQueryChange,
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                ),
                                cursorBrush = SolidColor(MaroonPrimary),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onQueryChange("") },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Patient Cards List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (filteredPatients.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No patients found matching \"$searchQuery\"",
                                    fontSize = 13.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    } else {
                        items(filteredPatients) { patient ->
                            val isSynced = patient.abhaId.endsWith("12") || patient.abhaId.endsWith("33")

                            PatientCard(
                                patient = patient,
                                isSynced = isSynced,
                                showSyncIcon = !isEmergencyReferral,
                                onClick = { onPatientSelect(patient) }
                            )
                        }
                    }
                }
            }

            // Bottom Wave Decorative Background Banner
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .align(Alignment.BottomCenter)
            ) {
                val path = Path().apply {
                    moveTo(0f, size.height * 0.4f)
                    cubicTo(
                        size.width * 0.35f, size.height * 0.1f,
                        size.width * 0.65f, size.height * 0.7f,
                        size.width, size.height * 0.3f
                    )
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(path, color = MaroonPrimary.copy(alpha = 0.5f))

                val frontPath = Path().apply {
                    moveTo(0f, size.height * 0.6f)
                    cubicTo(
                        size.width * 0.4f, size.height * 0.8f,
                        size.width * 0.7f, size.height * 0.3f,
                        size.width, size.height * 0.5f
                    )
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(frontPath, color = MaroonPrimary.copy(alpha = 0.75f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchPatientScreenPreview() {
    SehatTheme {
        SearchPatientScreen(
            searchQuery = "",
            patients = emptyList(),
            onQueryChange = {},
            onPatientSelect = {},
            onCreateAbha = { _, _, _, _, _, _ -> },
            onBackClick = {}
        )
    }
}
