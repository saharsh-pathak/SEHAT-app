package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.theme.*

@Composable
fun BasicScreeningTestsScreen(
    onNextClick: (
        bp: String, hr: Int, spo2: Int, temp: Float, bg: Int, hb: Float, wt: Float, ht: Float,
        pregnancy: String, malaria: String, dengue: String, urine: String, tb: String, notes: String
    ) -> Unit,
    onBackClick: () -> Unit
) {
    var bp by remember { mutableStateOf("120 / 80") }
    var hr by remember { mutableStateOf("88") }
    var spo2 by remember { mutableStateOf("98") }
    var temp by remember { mutableStateOf("37.8") }
    var bg by remember { mutableStateOf("110") }
    var hb by remember { mutableStateOf("11.2") }
    var wt by remember { mutableStateOf("58") }
    var ht by remember { mutableStateOf("162") }

    var pregTestChecked by remember { mutableStateOf(true) }
    var pregTestResult by remember { mutableStateOf("Positive") }
    var malariaChecked by remember { mutableStateOf(true) }
    var malariaResult by remember { mutableStateOf("Negative") }
    var dengueChecked by remember { mutableStateOf(false) }
    var urineChecked by remember { mutableStateOf(false) }
    var tbChecked by remember { mutableStateOf(false) }

    var notes by remember { mutableStateOf("Patient feels weak and has reduced appetite.") }

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Basic Screening Tests",
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                SehatButton(
                    text = "Next",
                    onClick = {
                        onNextClick(
                            bp, hr.toIntOrNull() ?: 88, spo2.toIntOrNull() ?: 98,
                            temp.toFloatOrNull() ?: 37.8f, bg.toIntOrNull() ?: 110,
                            hb.toFloatOrNull() ?: 11.2f, wt.toFloatOrNull() ?: 58f,
                            ht.toFloatOrNull() ?: 162f,
                            if (pregTestChecked) pregTestResult else "Not Conducted",
                            if (malariaChecked) malariaResult else "Not Conducted",
                            if (dengueChecked) "Negative" else "Not Conducted",
                            if (urineChecked) "Normal" else "Not Conducted",
                            if (tbChecked) "Negative" else "Not Conducted",
                            notes
                        )
                    }
                )
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
            item {
                Text("Vital Signs", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            // Vital Sign Inputs
            item { VitalRow(label = "Blood Pressure", value = bp, onValueChange = { bp = it }, unit = "mmHg") }
            item { VitalRow(label = "Heart Rate", value = hr, onValueChange = { hr = it }, unit = "bpm") }
            item { VitalRow(label = "SpO₂", value = spo2, onValueChange = { spo2 = it }, unit = "%") }
            item { VitalRow(label = "Temperature", value = temp, onValueChange = { temp = it }, unit = "°C") }
            item { VitalRow(label = "Blood Glucose (RBS)", value = bg, onValueChange = { bg = it }, unit = "mg/dL") }
            item { VitalRow(label = "Hemoglobin", value = hb, onValueChange = { hb = it }, unit = "g/dL") }
            item { VitalRow(label = "Weight", value = wt, onValueChange = { wt = it }, unit = "kg") }
            item { VitalRow(label = "Height", value = ht, onValueChange = { ht = it }, unit = "cm") }

            // Additional Tests Section
            item {
                Text("Additional Tests (if conducted)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(top = 8.dp))
            }

            item {
                TestCheckboxRow(
                    label = "Pregnancy Test",
                    checked = pregTestChecked,
                    onCheckedChange = { pregTestChecked = it },
                    result = pregTestResult,
                    onResultChange = { pregTestResult = it }
                )
            }
            item {
                TestCheckboxRow(
                    label = "Malaria Rapid Test",
                    checked = malariaChecked,
                    onCheckedChange = { malariaChecked = it },
                    result = malariaResult,
                    onResultChange = { malariaResult = it }
                )
            }

            // Additional Notes
            item {
                Text("Additional Notes", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun VitalRow(label: String, value: String, onValueChange: (String) -> Unit, unit: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, color = TextPrimary, modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(110.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(unit, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.width(44.dp))
        }
    }
}

@Composable
fun TestCheckboxRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    result: String,
    onResultChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = MaroonPrimary)
            )
            Text(label, fontSize = 14.sp, color = TextPrimary)
        }
        if (checked) {
            OutlinedTextField(
                value = result,
                onValueChange = onResultChange,
                singleLine = true,
                modifier = Modifier.width(110.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )
        }
    }
}
