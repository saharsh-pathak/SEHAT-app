package com.example.sehat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.theme.*

@Composable
fun ReferralManagementScreen(
    patientAbhaId: String,
    onCreateReferral: (facility: String, notes: String) -> Unit,
    onBackClick: () -> Unit
) {
    var destinationFacility by remember { mutableStateOf("PHC (Primary Health Centre)") }
    var notes by remember { mutableStateOf("Needs further evaluation and blood test.") }

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Referral Management",
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
                    text = "Create Referral",
                    onClick = {
                        onCreateReferral(destinationFacility, notes)
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
            item { Spacer(modifier = Modifier.height(4.dp)) }

            item {
                Text("Refer To Facility", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = destinationFacility,
                    onValueChange = { destinationFacility = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
            }

            // Patient Summary Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Patient Summary", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Sita Devi | 34 Female", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("ABHA ID: $patientAbhaId", fontSize = 13.sp, color = TextSecondary)
                    }
                }
            }

            // Current Condition Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Current Condition", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Upper Respiratory Tract Infection (URTI)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                        Text("Severity: Mild", fontSize = 13.sp, color = TextSecondary)
                    }
                }
            }

            // Additional Notes Input
            item {
                Text("Additional Notes", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Priority Auto-Assigned Notice
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaroonContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Priority will be assigned automatically based on clinical assessment.",
                        fontSize = 12.sp,
                        color = MaroonPrimary,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
