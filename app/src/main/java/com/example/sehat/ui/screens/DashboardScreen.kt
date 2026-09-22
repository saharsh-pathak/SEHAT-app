package com.example.sehat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.R
import com.example.sehat.ui.theme.*
import com.example.sehat.viewmodel.ScheduleItem

@Composable
fun DashboardScreen(
    pendingFollowUpsCount: Int,
    schedule: List<ScheduleItem>,
    onSearchPatientClick: () -> Unit,
    onMedicineClick: () -> Unit,
    onFollowUpClick: () -> Unit,
    onLanguageClick: () -> Unit
) {
    Scaffold(
        containerColor = CreamBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Top Header: Hamburger + Logo + Notifications
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaroonPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // SEHAT Emblem Logo
                    Image(
                        painter = painterResource(id = R.drawable.sehat_logo),
                        contentDescription = "SEHAT Logo",
                        modifier = Modifier.height(48.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Bell icon with badge dot
                    Box {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = MaroonPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC5221F))
                                .align(Alignment.TopEnd)
                                .offset(x = (-8).dp, y = (8).dp)
                        )
                    }
                }
            }

            // Profile Header + Location Chip
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF7EBEB)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaroonPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Good Morning,", fontSize = 12.sp, color = TextSecondary)
                            Text("Sunita Tai", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                            Text("Medical Worker – Khed Block", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
                        }
                    }

                    // Location Chip
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF5EFE6),
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaroonPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text("Khed Block", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("Maharashtra", fontSize = 10.sp, color = TextSecondary)
                            }
                        }
                    }
                }
            }

            // Start Patient Screening Main Card (Maroon Card)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaroonPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSearchPatientClick() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Text(
                            text = "PATIENT TIMELINE & SCREENING",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE8C5C5),
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Patient Timeline",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "View patient records, reports & sync status",
                            fontSize = 12.sp,
                            color = Color(0xFFF3E5E5)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // White button inside maroon card
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSearchPatientClick() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timeline,
                                    contentDescription = "Timeline",
                                    tint = MaroonPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Open Patient Timeline",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaroonPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Quick Actions Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quick Actions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                    Text(
                        text = "For your daily work",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            // Quick Actions 2x2 Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard(
                            title = "Follow-up Tasks",
                            subtitle = "Complete home visits",
                            icon = Icons.Default.Groups,
                            iconColor = MaroonPrimary,
                            iconBgColor = Color(0xFFF7EBEB),
                            cardBgColor = Color(0xFFFBF2ED),
                            onClick = onFollowUpClick,
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionCard(
                            title = "Medicine Availability",
                            subtitle = "Check stock at nearby facilities",
                            icon = Icons.Outlined.Medication,
                            iconColor = Color(0xFF2E7D32),
                            iconBgColor = Color(0xFFE8F5E9),
                            cardBgColor = Color(0xFFEFF7F0),
                            onClick = onMedicineClick,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickActionCard(
                            title = "View Patient History",
                            subtitle = "Past visits, reports, timeline",
                            icon = Icons.AutoMirrored.Outlined.Assignment,
                            iconColor = Color(0xFFD97706),
                            iconBgColor = Color(0xFFFEF3C7),
                            cardBgColor = Color(0xFFFAF4E8),
                            onClick = onSearchPatientClick,
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionCard(
                            title = "Emergency Referral",
                            subtitle = "For urgent cases",
                            icon = Icons.Default.Warning,
                            iconColor = Color(0xFFC5221F),
                            iconBgColor = Color(0xFFFFEBEE),
                            cardBgColor = Color(0xFFFDEAEA),
                            onClick = onSearchPatientClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Records Synced & Online Status Card
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Wifi,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "All records synced",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20)
                                )
                                Text(
                                    text = "Last synced: 2 minutes ago",
                                    fontSize = 11.sp,
                                    color = Color(0xFF388E3C)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                                    .background(Color(0xFFA5D6A7))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Online",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }
            }

            // Footer Slogan Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "आपल्या आरोग्यासाठी, आपल्या माणसांसोबत",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = null,
                            tint = MaroonPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Swasth Maharashtra, Samruddh Maharashtra",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    iconBgColor: Color,
    cardBgColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = cardBgColor,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = TextSecondary.copy(alpha = 0.6f),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                color = TextSecondary
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    SehatTheme {
        DashboardScreen(
            pendingFollowUpsCount = 5,
            schedule = emptyList(),
            onSearchPatientClick = {},
            onMedicineClick = {},
            onFollowUpClick = {},
            onLanguageClick = {}
        )
    }
}
