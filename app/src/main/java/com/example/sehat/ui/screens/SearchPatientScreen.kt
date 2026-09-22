package com.example.sehat.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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
    onQueryChange: (String) -> Unit,
    onPatientSelect: (Patient) -> Unit,
    onCreateAbha: (name: String, age: Int, gender: String, village: String, aadhaar: String, mobile: String) -> Unit,
    onBackClick: () -> Unit
) {
    val displayPatients = if (patients.isNotEmpty()) patients else listOf(
        Patient(abhaId = "1234 5678 9012", name = "Sita Devi", age = 34, gender = "Female", village = "Khed"),
        Patient(abhaId = "9876 5432 1098", name = "Ramesh Pawar", age = 52, gender = "Male", village = "Khed"),
        Patient(abhaId = "1111 2222 3333", name = "Lata Shinde", age = 28, gender = "Female", village = "Nandgaon"),
        Patient(abhaId = "9877 5658 4983", name = "SAHARSH", age = 19, gender = "Male", village = "Khed")
    )

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

                // Top Header Bar: Back Button + "Timeline"
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
                        text = "Timeline",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section Header: "Timeline" (No "View all")
                Text(
                    text = "Timeline",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
                )

                // Patient Cards List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(displayPatients) { patient ->
                        // Determine sync state: green tick for synced, gray tick for not synced
                        val isSynced = patient.abhaId.endsWith("12") || patient.abhaId.endsWith("33")

                        PatientCard(
                            patient = patient,
                            isSynced = isSynced,
                            onClick = { onPatientSelect(patient) }
                        )
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
                drawPath(path = path, color = Color(0xFF852A2A).copy(alpha = 0.85f))

                val subPath = Path().apply {
                    moveTo(0f, size.height * 0.6f)
                    cubicTo(
                        size.width * 0.4f, size.height * 0.3f,
                        size.width * 0.7f, size.height * 0.8f,
                        size.width, size.height * 0.5f
                    )
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(path = subPath, color = Color(0xFFD7CCC8).copy(alpha = 0.5f))
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
