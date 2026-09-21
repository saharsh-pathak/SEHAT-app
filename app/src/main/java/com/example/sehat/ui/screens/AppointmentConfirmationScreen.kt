package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Appointment
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.theme.*

@Composable
fun AppointmentConfirmationScreen(
    appointment: Appointment?,
    onDoneClick: () -> Unit
) {
    val appt = appointment ?: Appointment(
        appointmentCode = "APT250917001",
        referralId = 1,
        patientAbhaId = "1234 5678 9012",
        facility = "PHC (Primary Health Centre)",
        dateTime = "17 Sep 2025, 10:00 AM",
        queueToken = "Token #12"
    )

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                SehatButton(
                    text = "Done",
                    onClick = onDoneClick,
                    showArrow = false
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }

            // Green Checkmark Header
            item {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(SeverityMild),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            item {
                Text(
                    text = "Appointment Booked\nAutomatically",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SeverityMild,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Patient has been added to the queue at the selected facility.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Read-Only Confirmation Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        DetailField(label = "Appointment ID", value = appt.appointmentCode)
                        DetailField(label = "Facility", value = appt.facility)
                        DetailField(label = "Date & Time", value = appt.dateTime)
                        DetailField(label = "Patient ABHA", value = appt.patientAbhaId)
                        DetailField(label = "Consultation Type", value = appt.consultationType)
                        DetailField(label = "Queue Token", value = appt.queueToken)
                    }
                }
            }

            // Info Notice
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "The patient can visit the facility on the given date and time. No manual booking is required.",
                        fontSize = 12.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun DetailField(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = TextSecondary)
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}
