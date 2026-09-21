package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun LanguageSelectionScreen(
    selectedLanguage: String,
    onLanguageSelect: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val languages = listOf(
        "Marathi" to "मराठी (Marathi)",
        "Hindi" to "हिंदी (Hindi)"
    )

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Language Selection",
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
                    text = "Apply Language",
                    onClick = onBackClick,
                    showArrow = false
                )
            }
        },
        containerColor = CreamBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select your preferred language for Voice STT & TTS",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            languages.forEach { (code, label) ->
                val isSelected = selectedLanguage.equals(code, ignoreCase = true)
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaroonContainer else Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLanguageSelect(code) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaroonPrimary else TextPrimary
                        )
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaroonPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
