package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.entity.Patient
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.theme.*
import com.example.sehat.viewmodel.TimelineItemData

@Composable
fun PatientTimelineScreen(
    patient: Patient?,
    timelineItems: List<TimelineItemData>,
    onCreateReferralClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Timeline, 1: Reports, 2: Prescriptions, 3: Follow-ups

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Patient Timeline",
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
                    onClick = onCreateReferralClick
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
            // Patient Header Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaroonContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = MaroonPrimary, modifier = Modifier.size(30.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(patient?.name ?: "Sita Devi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("ABHA ID: ${patient?.abhaId ?: "1234 5678 9012"}", fontSize = 13.sp, color = TextSecondary)
                            Text("Age: ${patient?.age ?: 34} | ${patient?.gender ?: "Female"} | Village: ${patient?.village ?: "Khed"}", fontSize = 12.sp, color = TextMuted)
                        }
                    }
                }
            }

            // Tabs Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CreamSurface, shape = RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val tabs = listOf("Timeline", "Reports", "Prescriptions", "Follow-ups")
                    tabs.forEachIndexed { index, title ->
                        TextButton(
                            onClick = { selectedTab = index },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (selectedTab == index) MaroonPrimary else TextSecondary
                            )
                        ) {
                            Text(title, fontSize = 12.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }

            // Timeline Items
            items(timelineItems) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    // Vertical Timeline Indicator
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaroonPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(item.dayBadge, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(60.dp)
                                .background(SurfaceVariant)
                        )
                    }

                    // Content Card
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(item.date, fontSize = 11.sp, color = TextMuted)
                            Text(item.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(item.subtitle, fontSize = 13.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(item.details, fontSize = 12.sp, color = TextMuted)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
