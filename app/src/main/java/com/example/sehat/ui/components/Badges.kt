package com.example.sehat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.theme.*

@Composable
fun SeverityBadge(severity: String) {
    val (bgColor, textColor) = when (severity.lowercase()) {
        "mild" -> SeverityMildBg to SeverityMild
        "moderate" -> SeverityModerateBg to SeverityModerate
        "severe" -> SeveritySevereBg to SeveritySevere
        "emergency" -> SeverityEmergencyBg to SeverityEmergency
        else -> SeverityMildBg to SeverityMild
    }

    Text(
        text = severity,
        color = textColor,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Composable
fun StockStatusBadge(status: String) {
    val (bgColor, textColor) = when (status) {
        "In Stock" -> SeverityMildBg to StockInStock
        "Low Stock" -> SeverityModerateBg to StockLowStock
        "Unavailable" -> SeveritySevereBg to StockUnavailable
        else -> SeverityMildBg to StockInStock
    }

    Text(
        text = status,
        color = textColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
