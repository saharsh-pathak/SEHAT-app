package com.example.sehat.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Patient
import com.example.sehat.ui.components.PatientCard
import com.example.sehat.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPatientScreen(
    searchQuery: String,
    patients: List<Patient>,
    screenTitle: String = "Search Patient",
    isEmergencyReferral: Boolean = false,
    onQueryChange: (String) -> Unit,
    onPatientSelect: (Patient) -> Unit,
    onCreateAbha: (name: String, age: Int, gender: String, village: String, aadhaar: String, mobile: String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var showAddPatientSheet by remember { mutableStateOf(false) }

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

                // Top Header Bar: Back Button + Dynamic Title
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
                        text = screenTitle,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search Box
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 1.dp,
                    border = BorderStroke(1.dp, Color(0xFFE8E0D5)),
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
                                    fontSize = 12.sp,
                                    color = TextMuted,
                                    maxLines = 1
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = onQueryChange,
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 13.sp,
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
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFFFBEBEB),
                                        modifier = Modifier.size(64.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.PersonAdd,
                                                contentDescription = null,
                                                tint = MaroonPrimary,
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "No patients found matching \"$searchQuery\"",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextSecondary
                                    )

                                    Spacer(modifier = Modifier.height(18.dp))

                                    // + Add Patient Button
                                    Button(
                                        onClick = { showAddPatientSheet = true },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaroonPrimary,
                                            contentColor = Color.White
                                        ),
                                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Add Patient",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
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

        // Add Patient Bottom Sheet Modal (Aadhaar -> Temporary ABHA -> Screening)
        if (showAddPatientSheet) {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            var aadhaarInput by remember { mutableStateOf("") }
            var nameInput by remember {
                mutableStateOf(if (searchQuery.any { it.isLetter() }) searchQuery else "")
            }
            var mobileInput by remember {
                mutableStateOf(if (searchQuery.all { it.isDigit() } && searchQuery.length in 5..10) searchQuery else "")
            }
            var ageInput by remember { mutableStateOf("45") }
            var selectedGender by remember { mutableStateOf("Male") }
            var villageInput by remember { mutableStateOf("Khed") }

            ModalBottomSheet(
                onDismissRequest = { showAddPatientSheet = false },
                sheetState = sheetState,
                containerColor = CreamBackground,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 32.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Register New Patient",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaroonPrimary
                            )
                            Text(
                                text = "Collect Aadhaar ID to generate temporary ABHA and start screening.",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }

                        IconButton(onClick = { showAddPatientSheet = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Step 1: Aadhaar ID (Required)
                    OutlinedTextField(
                        value = aadhaarInput,
                        onValueChange = { if (it.length <= 12) aadhaarInput = it.filter { char -> char.isDigit() } },
                        label = { Text("Aadhaar Number (12 digits) *") },
                        placeholder = { Text("e.g. 5642 8910 2341") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = MaroonPrimary,
                            focusedBorderColor = MaroonPrimary,
                            unfocusedBorderColor = Color(0xFFE0D8D0)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Full Name
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Patient Full Name *") },
                        placeholder = { Text("e.g. Ramesh Kadam") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = MaroonPrimary,
                            focusedBorderColor = MaroonPrimary,
                            unfocusedBorderColor = Color(0xFFE0D8D0)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mobile Number & Age in a row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = mobileInput,
                            onValueChange = { if (it.length <= 10) mobileInput = it.filter { char -> char.isDigit() } },
                            label = { Text("Mobile Number") },
                            placeholder = { Text("9876543210") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                cursorColor = MaroonPrimary,
                                focusedBorderColor = MaroonPrimary,
                                unfocusedBorderColor = Color(0xFFE0D8D0)
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = ageInput,
                            onValueChange = { if (it.length <= 3) ageInput = it.filter { char -> char.isDigit() } },
                            label = { Text("Age *") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                cursorColor = MaroonPrimary,
                                focusedBorderColor = MaroonPrimary,
                                unfocusedBorderColor = Color(0xFFE0D8D0)
                            ),
                            modifier = Modifier.width(90.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Gender Selector
                    Text("Gender *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf("Male", "Female", "Other").forEach { gender ->
                            val isSelected = selectedGender == gender
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MaroonPrimary else Color.White,
                                border = BorderStroke(1.dp, if (isSelected) MaroonPrimary else Color(0xFFE0D8D0)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedGender = gender }
                            ) {
                                Text(
                                    text = gender,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else TextPrimary,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Village / Ward
                    OutlinedTextField(
                        value = villageInput,
                        onValueChange = { villageInput = it },
                        label = { Text("Village / Ward") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = MaroonPrimary,
                            focusedBorderColor = MaroonPrimary,
                            unfocusedBorderColor = Color(0xFFE0D8D0)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // ABHA Linking Notice Card
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.dp, Color(0xFFF59E0B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "A temporary ABHA-linked patient profile will be generated automatically and linked to this screening.",
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                color = Color(0xFF92400E)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit & Continue to Screening Button
                    Button(
                        onClick = {
                            if (aadhaarInput.isBlank() || nameInput.isBlank()) {
                                Toast.makeText(context, "Please enter Aadhaar ID and Patient Name", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val ageInt = ageInput.toIntOrNull() ?: 35
                            showAddPatientSheet = false
                            onCreateAbha(
                                nameInput.trim(),
                                ageInt,
                                selectedGender,
                                villageInput.trim().ifBlank { "Khed" },
                                aadhaarInput.trim(),
                                mobileInput.trim().ifBlank { "9800000000" }
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaroonPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Create ABHA & Start Screening →",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
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
            screenTitle = "Search Patient",
            onQueryChange = {},
            onPatientSelect = {},
            onCreateAbha = { _, _, _, _, _, _ -> },
            onBackClick = {}
        )
    }
}
