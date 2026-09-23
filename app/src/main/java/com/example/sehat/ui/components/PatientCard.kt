package com.example.sehat.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Patient
import com.example.sehat.ui.theme.TextMuted
import com.example.sehat.ui.theme.TextPrimary
import com.example.sehat.ui.theme.TextSecondary

@Composable
fun PatientCard(
    patient: Patient,
    isSynced: Boolean = true,
    showSyncIcon: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Fake avatar
            PatientAvatar(
                name = patient.name,
                gender = patient.gender,
                size = 52.dp
            )

            Spacer(modifier = Modifier.width(14.dp))

            // Patient details column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = patient.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "ABHA ID: ${patient.abhaId}",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Text(
                    text = "${patient.age} Yrs • ${patient.gender} • ${patient.village}",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            if (showSyncIcon) {
                // Sync symbol: green tick for synced, RED tick for not synced
                Surface(
                    shape = CircleShape,
                    color = if (isSynced) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    modifier = Modifier.size(30.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = if (isSynced) "Synced" else "Not Synced",
                            tint = if (isSynced) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
