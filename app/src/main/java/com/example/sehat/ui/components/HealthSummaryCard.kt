package com.example.sehat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.theme.*

@Composable
fun HealthSummaryCard(
    healthStatus: String,
    lastScreeningDate: String,
    activeCareEpisode: String,
    chronicConditions: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F5E9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonitorHeart,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Overall Assessment", fontSize = 11.sp, color = TextSecondary)
                        Text(healthStatus, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF7EBEB)
                ) {
                    Text(
                        text = "Last: $lastScreeningDate",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaroonPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFEDE7E0), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Text("Chronic Conditions / Risk Tags", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                chronicConditions.forEach { condition ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = when (condition) {
                            "Hypertension" -> Color(0xFFFFF3E0)
                            "Diabetes" -> Color(0xFFE3F2FD)
                            "Pregnancy" -> Color(0xFFFCE4EC)
                            else -> Color(0xFFF5EFE6)
                        }
                    ) {
                        Text(
                            text = condition,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = when (condition) {
                                "Hypertension" -> Color(0xFFE65100)
                                "Diabetes" -> Color(0xFF1565C0)
                                "Pregnancy" -> Color(0xFFC2185B)
                                else -> TextPrimary
                            },
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}
