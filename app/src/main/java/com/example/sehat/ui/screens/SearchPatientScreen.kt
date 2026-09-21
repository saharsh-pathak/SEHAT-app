package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Patient
import com.example.sehat.ui.components.PatientCard
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.components.SehatTopBar
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
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Search Existing, 1: Create New

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Search for Patient",
                onBackClick = onBackClick
            )
        },
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            // Tab Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(CreamSurface, shape = RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                Button(
                    onClick = { selectedTab = 0 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 0) MaroonPrimary else Color.Transparent,
                        contentColor = if (selectedTab == 0) Color.White else TextPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Search Existing",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedTab == 0) Color.White else TextPrimary
                    )
                }
                Button(
                    onClick = { selectedTab = 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 1) MaroonPrimary else Color.Transparent,
                        contentColor = if (selectedTab == 1) Color.White else TextPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Create New",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedTab == 1) Color.White else TextPrimary
                    )
                }
            }

            if (selectedTab == 0) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = { Text("Enter ABHA ID / Name / Mobile Number") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaroonPrimary) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = MaroonPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = "Recent Patients",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(patients) { patient ->
                        PatientCard(patient = patient, onClick = { onPatientSelect(patient) })
                    }
                }


            } else {
                // Create ABHA Form
                var name by remember { mutableStateOf("") }
                var age by remember { mutableStateOf("") }
                var gender by remember { mutableStateOf("Female") }
                var village by remember { mutableStateOf("Khed") }
                var aadhaar by remember { mutableStateOf("") }
                var mobile by remember { mutableStateOf("") }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        OutlinedTextField(
                            value = name, onValueChange = { name = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = age, onValueChange = { age = it },
                            label = { Text("Age") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = gender, onValueChange = { gender = it },
                            label = { Text("Gender (Male/Female/Other)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = village, onValueChange = { village = it },
                            label = { Text("Village / Gram Panchayat") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = aadhaar, onValueChange = { aadhaar = it },
                            label = { Text("Aadhaar Number (12 digits)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = mobile, onValueChange = { mobile = it },
                            label = { Text("Mobile Number") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        SehatButton(
                            text = "Create ABHA & Register",
                            onClick = {
                                if (name.isNotBlank()) {
                                    onCreateAbha(name, age.toIntOrNull() ?: 30, gender, village, aadhaar, mobile)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun SearchPatientScreenPreview() {
    SehatTheme {
        SearchPatientScreen(
            searchQuery = "",
            patients = listOf(
                Patient(abhaId = "1234 5678 9012", name = "Sita Devi", age = 34, gender = "Female", village = "Khed"),
                Patient(abhaId = "9876 5432 1098", name = "Ramesh Pawar", age = 52, gender = "Male", village = "Khed")
            ),
            onQueryChange = {},
            onPatientSelect = {},
            onCreateAbha = { _, _, _, _, _, _ -> },
            onBackClick = {}
        )
    }
}
