package com.example.sehat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.data.model.TimelineEvent
import com.example.sehat.data.model.TimelineEventType
import com.example.sehat.ui.theme.*

@Composable
fun TimelineItem(
    event: TimelineEvent,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    val nodeColor = Color(event.statusColorHex)
    val eventIcon: ImageVector = when (event.eventType) {
        TimelineEventType.SCREENING -> Icons.Default.Search
        TimelineEventType.PHC_CONSULTATION -> Icons.Default.MedicalServices
        TimelineEventType.FOLLOW_UP -> Icons.Default.HomeWork
        TimelineEventType.DIAGNOSTICS -> Icons.Default.MonitorHeart
        TimelineEventType.REFERRAL -> Icons.Default.LocalHospital
        TimelineEventType.HOSPITAL_ADMISSION -> Icons.Default.Hotel
        TimelineEventType.DISCHARGE -> Icons.Default.CheckCircle
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Timeline track (Dot + Vertical Line)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(28.dp)
                .padding(top = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(nodeColor)
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(Color(0xFFE5DDD0))
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Content Box
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = event.date,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = event.time,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = event.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "${event.description} • ${event.facility}",
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )
        }
    }
}
