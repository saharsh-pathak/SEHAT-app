package com.example.sehat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.components.SeverityBadge
import com.example.sehat.ui.theme.*
import com.example.sehat.util.VoiceManager
import com.example.sehat.viewmodel.CareEpisodeState

@Composable
fun TriageResultScreen(
    state: CareEpisodeState,
    onNotesChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val voiceManager = remember { VoiceManager(context) }
    var selectedFacility by remember { mutableStateOf(state.recommendedFacility) }
    var selectedProfessional by remember { mutableStateOf(state.recommendedProfessional) }
    var clinicalNotes by remember { mutableStateOf(state.clinicalNotes) }
    var readLanguage by remember { mutableStateOf("Marathi") }

    DisposableEffect(Unit) {
        onDispose {
            voiceManager.destroy()
        }
    }

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Triage Result",
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
                    text = "Confirm & Continue",
                    onClick = {
                        voiceManager.stopSpeak()
                        onNotesChange(clinicalNotes)
                        onConfirmClick()
                    }
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
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Audio Readout Controls
            item {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Audio Readout (TTS)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            listOf("Marathi", "Hindi", "English").forEach { lang ->
                                TextButton(onClick = { readLanguage = lang }) {
                                    Text(
                                        lang,
                                        fontSize = 11.sp,
                                        color = if (readLanguage == lang) MaroonPrimary else TextMuted,
                                        fontWeight = if (readLanguage == lang) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                            IconButton(
                                onClick = {
                                    val textToRead = "Probable Condition: ${state.probableCondition}. Severity: ${state.severity}. ${state.justification}"
                                    voiceManager.speak(textToRead, readLanguage)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Read Aloud",
                                    tint = MaroonPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Probable Condition Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Probable Condition", fontSize = 12.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(state.probableCondition, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                        Text("Based on symptoms and test results", fontSize = 11.sp, color = TextMuted)
                    }
                }
            }

            // Severity Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SeverityMildBg),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Severity", fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            SeverityBadge(state.severity)
                        }
                        Text("Likely to recover at primary care level.\nMonitor symptoms.", fontSize = 11.sp, color = TextSecondary)
                    }
                }
            }

            // Recommended Facility
            item {
                Text("Recommended Facility", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = selectedFacility,
                    onValueChange = { selectedFacility = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
            }

            // Recommended Healthcare Professional
            item {
                Text("Recommended Healthcare Professional", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = selectedProfessional,
                    onValueChange = { selectedProfessional = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
            }

            // Why this recommendation? Justification Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Info, contentDescription = null, tint = MaroonPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Why this recommendation?", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(state.justification, fontSize = 13.sp, color = TextPrimary, lineHeight = 18.sp)
                    }
                }
            }

            // Additional Clinical Notes
            item {
                Text("Additional Clinical Notes (Required)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = clinicalNotes,
                    onValueChange = { clinicalNotes = it },
                    placeholder = { Text("Patient reports mild fever for 3 days and cough...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
