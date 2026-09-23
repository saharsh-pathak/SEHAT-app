package com.example.sehat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.theme.MaroonPrimary
import com.example.sehat.ui.theme.TextPrimary
import com.example.sehat.ui.theme.TextSecondary

data class LanguageItem(
    val id: String,
    val displayName: String
)

val INDIAN_LANGUAGES = listOf(
    LanguageItem("mr", "Marathi (मराठी)"),
    LanguageItem("hi", "Hindi (हिंदी)"),
    LanguageItem("en", "English"),
    LanguageItem("gu", "Gujarati (ગુજરાતી)"),
    LanguageItem("bn", "Bengali (বাংলা)"),
    LanguageItem("ta", "Tamil (தமிழ்)"),
    LanguageItem("te", "Telugu (తెలుగు)"),
    LanguageItem("kn", "Kannada (ಕನ್ನಡ)"),
    LanguageItem("ml", "Malayalam (മലയാളം)"),
    LanguageItem("pa", "Punjabi (ਪੰਜਾਬੀ)"),
    LanguageItem("or", "Odia (ଓଡ଼ିଆ)"),
    LanguageItem("as", "Assamese (অসমীয়া)")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionModal(
    initialLanguage: String = "Marathi (मराठी)",
    onDismiss: () -> Unit,
    onStartScreening: (LanguageItem) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedLanguage by remember {
        mutableStateOf(
            INDIAN_LANGUAGES.find { it.displayName == initialLanguage || it.displayName.startsWith(initialLanguage) }
                ?: INDIAN_LANGUAGES.first()
        )
    }

    val filteredLanguages = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            INDIAN_LANGUAGES
        } else {
            INDIAN_LANGUAGES.filter {
                it.displayName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = Color(0xFFFFFBF7),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(44.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFD6CEC5))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            // Header Row: Globe Icon + Title + Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFBEBEB)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Language,
                        contentDescription = "Language",
                        tint = MaroonPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Select Conversation Language",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaroonPrimary,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF555555),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            Text(
                text = "Choose the language that will be used for the entire patient screening conversation.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Box with Dropdown Arrow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFD0C8B8), RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = TextPrimary
                        ),
                        cursorBrush = SolidColor(MaroonPrimary),
                        modifier = Modifier.weight(1f),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search language...",
                                    fontSize = 14.sp,
                                    color = Color(0xFF9E9E9E)
                                )
                            }
                            innerTextField()
                        }
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = Color(0xFF757575),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Scrollable Language List
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEDE7DD)),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 6.dp)
                ) {
                    items(filteredLanguages) { lang ->
                        val isSelected = lang.id == selectedLanguage.id
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFFFDEAEA) else Color.Transparent)
                                .clickable { selectedLanguage = lang }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = lang.displayName,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) MaroonPrimary else Color(0xFF2C2C2C)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Warning Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFF3E0),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE0B2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Warning",
                            tint = Color(0xFFE65100),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "The selected language cannot be changed once the screening has started.",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF5D4037)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Primary CTA: Start Screening ->
            Button(
                onClick = { onStartScreening(selectedLanguage) },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaroonPrimary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Start Screening",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
