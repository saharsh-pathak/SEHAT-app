package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sehat.ui.theme.*
import com.example.sehat.viewmodel.DynamicTestModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicScreeningTestsScreen(
    initialBp: String = "160/100",
    initialHr: Int = 94,
    initialSpo2: Int = 92,
    initialTemp: Float = 38.6f,
    initialHt: Float = 172f,
    initialWt: Float = 76f,
    initialTests: List<DynamicTestModel> = listOf(
        DynamicTestModel(testName = "Blood Glucose (RBS)", result = "138", unit = "mg/dL", remark = "Post-meal sample"),
        DynamicTestModel(testName = "Hemoglobin", result = "12.4", unit = "g/dL", remark = "Within normal limit"),
        DynamicTestModel(testName = "Malaria RDT", result = "Negative", unit = "", remark = "Rapid antigen card"),
        DynamicTestModel(testName = "Dengue NS1", result = "Negative", unit = "", remark = "Rapid cassette")
    ),
    initialObservations: String = "Patient presented with severe fatigue, diaphoresis, and acute dizziness upon standing. Auscultation reveals bilateral coarse crepitations in lower lobes. Peripheral pulses bounding, heart sounds normal with tachycardia.",
    onNextClick: (
        bp: String, hr: Int, spo2: Int, temp: Float, ht: Float, wt: Float,
        tests: List<DynamicTestModel>, observations: String
    ) -> Unit,
    onBackClick: () -> Unit
) {
    var bp by remember { mutableStateOf(initialBp) }
    var hr by remember { mutableStateOf(initialHr.toString()) }
    var spo2 by remember { mutableStateOf(initialSpo2.toString()) }
    var temp by remember { mutableStateOf(initialTemp.toString()) }
    var ht by remember { mutableStateOf(initialHt.toInt().toString()) }
    var wt by remember { mutableStateOf(initialWt.toInt().toString()) }

    val dynamicTests = remember { mutableStateListOf<DynamicTestModel>().apply { addAll(initialTests) } }
    var observations by remember { mutableStateOf(initialObservations) }

    // Dialog state for Add / Edit Test
    var showTestDialog by remember { mutableStateOf(false) }
    var editingTestId by remember { mutableStateOf<String?>(null) }
    var testNameInput by remember { mutableStateOf("") }
    var testResultInput by remember { mutableStateOf("") }
    var testUnitInput by remember { mutableStateOf("") }
    var testRemarkInput by remember { mutableStateOf("") }

    if (showTestDialog) {
        Dialog(onDismissRequest = { showTestDialog = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = if (editingTestId != null) "Edit Test" else "Add Medical Test",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = testNameInput,
                        onValueChange = { testNameInput = it },
                        label = { Text("Test Name (e.g. Blood Glucose)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = testResultInput,
                            onValueChange = { testResultInput = it },
                            label = { Text("Result / Value") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = testUnitInput,
                            onValueChange = { testUnitInput = it },
                            label = { Text("Unit (optional)") },
                            modifier = Modifier.weight(0.7f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = testRemarkInput,
                        onValueChange = { testRemarkInput = it },
                        label = { Text("Remark (optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTestDialog = false }) {
                            Text("Cancel", color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (testNameInput.isNotBlank() && testResultInput.isNotBlank()) {
                                    val id = editingTestId
                                    if (id != null) {
                                        val idx = dynamicTests.indexOfFirst { it.id == id }
                                        if (idx != -1) {
                                            dynamicTests[idx] = dynamicTests[idx].copy(
                                                testName = testNameInput.trim(),
                                                result = testResultInput.trim(),
                                                unit = testUnitInput.trim(),
                                                remark = testRemarkInput.trim()
                                            )
                                        }
                                    } else {
                                        dynamicTests.add(
                                            DynamicTestModel(
                                                testName = testNameInput.trim(),
                                                result = testResultInput.trim(),
                                                unit = testUnitInput.trim(),
                                                remark = testRemarkInput.trim()
                                            )
                                        )
                                    }
                                    showTestDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(if (editingTestId != null) "Save Changes" else "Add Test")
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
                            text = "Medical Vitals & Tests",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Text(
                            text = "Step 2 of 4 • Diagnostic Measurements",
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            onNextClick(
                                bp,
                                hr.toIntOrNull() ?: 94,
                                spo2.toIntOrNull() ?: 92,
                                temp.toFloatOrNull() ?: 38.6f,
                                ht.toFloatOrNull() ?: 172f,
                                wt.toFloatOrNull() ?: 76f,
                                dynamicTests.toList(),
                                observations
                            )
                        },
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
                            text = "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Section 1: Basic Vitals
            item {
                Text(
                    text = "Basic Vitals",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaroonPrimary
                )
            }

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
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            VitalInputField(
                                label = "Blood Pressure",
                                value = bp,
                                onValueChange = { bp = it },
                                unit = "mmHg",
                                modifier = Modifier.weight(1f)
                            )
                            VitalInputField(
                                label = "Heart Rate",
                                value = hr,
                                onValueChange = { hr = it },
                                unit = "bpm",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            VitalInputField(
                                label = "SpO₂",
                                value = spo2,
                                onValueChange = { spo2 = it },
                                unit = "%",
                                isCritical = (spo2.toIntOrNull() ?: 100) < 95,
                                modifier = Modifier.weight(1f)
                            )
                            VitalInputField(
                                label = "Temperature",
                                value = temp,
                                onValueChange = { temp = it },
                                unit = "°C",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            VitalInputField(
                                label = "Height",
                                value = ht,
                                onValueChange = { ht = it },
                                unit = "cm",
                                modifier = Modifier.weight(1f)
                            )
                            VitalInputField(
                                label = "Weight",
                                value = wt,
                                onValueChange = { wt = it },
                                unit = "kg",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Section 2: Dynamic Tests Conducted
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Tests Conducted (${dynamicTests.size})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Text(
                            text = "Dynamic test records",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    Button(
                        onClick = {
                            editingTestId = null
                            testNameInput = ""
                            testResultInput = ""
                            testUnitInput = ""
                            testRemarkInput = ""
                            showTestDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaroonPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("+ Add Test", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Render Unlimited Dynamic Tests
            items(dynamicTests, key = { it.id }) { test ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEDE7DD)),
                    modifier = Modifier.fillMaxWidth()
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
                                text = test.testName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (test.unit.isNotBlank()) "${test.result} ${test.unit}" else test.result,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaroonPrimary
                                )
                                if (test.remark.isNotBlank()) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "• ${test.remark}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    editingTestId = test.id
                                    testNameInput = test.testName
                                    testResultInput = test.result
                                    testUnitInput = test.unit
                                    testRemarkInput = test.remark
                                    showTestDialog = true
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = MaroonPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = { dynamicTests.remove(test) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Delete",
                                    tint = Color(0xFFC5221F),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Section 3: Clinical Observations
            item {
                Text(
                    text = "Clinical Observations",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaroonPrimary
                )
            }

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
                        OutlinedTextField(
                            value = observations,
                            onValueChange = { observations = it },
                            placeholder = {
                                Text(
                                    "Enter additional observations made during physical examination...",
                                    fontSize = 13.sp,
                                    color = TextMuted
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaroonPrimary,
                                unfocusedBorderColor = Color(0xFFEDE7DD)
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
        }
    }
}

@Composable
private fun VitalInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    isCritical: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isCritical) Color(0xFFFFEBEE) else Color(0xFFFAF7F2),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCritical) Color(0xFFC5221F).copy(alpha = 0.5f) else Color(0xFFE8E0D5)
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isCritical) Color(0xFFC5221F) else TextSecondary
                )
                Text(
                    text = unit,
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCritical) Color(0xFFC5221F) else TextPrimary
                ),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(MaroonPrimary),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
