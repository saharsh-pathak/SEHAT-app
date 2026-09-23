package com.example.sehat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.R
import com.example.sehat.ui.theme.*
import com.example.sehat.viewmodel.CareEpisodeState

@Composable
fun AppointmentConfirmationScreen(
    state: CareEpisodeState,
    onViewPatientDetailsClick: () -> Unit,
    onGoToDashboardClick: () -> Unit
) {
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
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Screening Completed",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Text(
                            text = "Step 4 of 4 • Submission & Next Steps",
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onViewPatientDetailsClick,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaroonPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonPrimary),
                        modifier = Modifier
                            .weight(0.45f)
                            .height(52.dp)
                    ) {
                        Text(
                            text = "View Patient Details",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = onGoToDashboardClick,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaroonPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(0.55f)
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Go to Dashboard",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
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
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // 1. Green Success Card
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFE8F5E9),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF81C784)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2E7D32)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Success",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Screening Submitted Successfully",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Patient health records updated and referral generated.",
                            fontSize = 12.sp,
                            color = Color(0xFF388E3C),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // 2. Severity Summary Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Severity Level",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = state.probableCondition,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (state.severity.equals("High", ignoreCase = true)) Color(0xFFC5221F) else MaroonPrimary
                        ) {
                            Text(
                                text = state.severity.uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // 3. Appointment Summary Card
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Auto-Booked Appointment",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaroonPrimary
                            )

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFEDE7DD)
                            ) {
                                Text(
                                    text = state.autoAppointmentToken,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaroonPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        SummaryInfoRow("Destination Facility", state.recommendedFacility)
                        SummaryInfoRow("Department", state.recommendedDepartment)
                        SummaryInfoRow("Appointment Date", state.autoAppointmentDate)
                        SummaryInfoRow("Digital Referral ID", state.generatedReferralId)
                    }
                }
            }

            // 4. What Happens Next? Checklist
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
                        Text(
                            text = "What Happens Next?",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        NextStepItem("Patient record updated in local and sync registry.")
                        NextStepItem("ABHA-linked digital health record updated.")
                        NextStepItem("Referral sent digitally to destination facility queue.")
                        NextStepItem("Follow-up task scheduled after specialist closes episode.")
                    }
                }
            }

            // Divider Line
            item {
                HorizontalDivider(
                    color = Color(0xFFE8E0D5),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // 5. Bilingual Footer (Matching Dashboard)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.footer_leaf),
                        contentDescription = "Leaves",
                        modifier = Modifier.size(52.dp),
                        contentScale = ContentScale.Fit
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = "“स्वस्थ महाराष्ट्र,\nसमृद्ध महाराष्ट्र”",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "“Healthy Maharashtra,\nProsperous Maharashtra”",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaroonPrimary.copy(alpha = 0.85f),
                            textAlign = TextAlign.Center,
                            lineHeight = 15.sp
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.footer_map),
                        contentDescription = "Maharashtra Map",
                        modifier = Modifier.size(52.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
    }
}

@Composable
private fun NextStepItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = Color(0xFF333333)
        )
    }
}
