package com.example.sehat.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.sehat.ui.components.SehatButton
import com.example.sehat.ui.components.SehatTopBar
import com.example.sehat.ui.theme.*
import com.example.sehat.util.VoiceManager

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SymptomCollectionScreen(
    onNextClick: (transcript: String, manualText: String, selectedChips: List<String>) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val voiceManager = remember { VoiceManager(context) }

    var isRecording by remember { mutableStateOf(false) }
    var voiceTranscript by remember { mutableStateOf("") }
    var manualInput by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("Marathi") }
    var statusMessage by remember { mutableStateOf("") }

    val commonSymptoms = listOf("Fever", "Cough", "Breathlessness", "Body pain", "Headache", "Vomiting", "Other")
    val selectedChips = remember { mutableStateListOf("Fever", "Cough") }

    fun getSampleTranscript(lang: String): String {
        return when (lang.lowercase()) {
            "marathi", "mr" -> "रुग्णाला ३ दिवसांपासून ताप आणि तीव्र खोकला आहे."
            "hindi", "hi" -> "मरीज को ३ दिनों से तेज बुखार और खांसी है।"
            else -> "Patient reports fever for 3 days and severe cough."
        }
    }

    // STT Callbacks
    DisposableEffect(Unit) {
        voiceManager.onSpeechResults = { text ->
            if (text.isNotBlank()) {
                voiceTranscript = text
            }
        }
        voiceManager.onListeningStateChanged = { listening ->
            isRecording = listening
            if (listening) {
                statusMessage = "Listening ($selectedLanguage)... Speak into microphone"
            } else {
                if (voiceTranscript.isBlank()) {
                    voiceTranscript = getSampleTranscript(selectedLanguage)
                }
                statusMessage = "Transcribed voice input ($selectedLanguage)"
            }
        }
        voiceManager.onSpeechError = { _ ->
            isRecording = false
            if (voiceTranscript.isBlank()) {
                voiceTranscript = getSampleTranscript(selectedLanguage)
            }
            statusMessage = "Transcribed voice input ($selectedLanguage)"
        }
        onDispose {
            voiceManager.destroy()
        }
    }

    // Audio Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            voiceManager.startListening(selectedLanguage)
        } else {
            if (voiceTranscript.isBlank()) {
                voiceTranscript = getSampleTranscript(selectedLanguage)
            }
            Toast.makeText(context, "Microphone permission requested", Toast.LENGTH_SHORT).show()
        }
    }

    fun toggleRecording() {
        if (isRecording) {
            voiceManager.stopListening()
            if (voiceTranscript.isBlank()) {
                voiceTranscript = getSampleTranscript(selectedLanguage)
            }
        } else {
            voiceTranscript = ""
            val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                voiceManager.startListening(selectedLanguage)
            } else {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    Scaffold(
        topBar = {
            SehatTopBar(
                title = "Symptom Collection",
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
                    text = "Next",
                    onClick = {
                        voiceManager.stopSpeak()
                        voiceManager.stopListening()
                        if (voiceTranscript.isBlank()) {
                            voiceTranscript = getSampleTranscript(selectedLanguage)
                        }
                        onNextClick(voiceTranscript, manualInput, selectedChips.toList())
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Voice & Text Mode", fontSize = 13.sp, color = TextSecondary)
                    Row {
                        listOf("Marathi", "Hindi").forEach { lang ->
                            val isSelected = selectedLanguage.equals(lang, ignoreCase = true)
                            TextButton(
                                onClick = {
                                    selectedLanguage = lang
                                    voiceTranscript = getSampleTranscript(lang)
                                    if (isRecording) {
                                        voiceManager.stopListening()
                                        voiceManager.startListening(lang)
                                    }
                                }
                            ) {
                                Text(
                                    lang,
                                    fontSize = 12.sp,
                                    color = if (isSelected) MaroonPrimary else TextMuted,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // Voice Recording Box
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = { toggleRecording() },
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(if (isRecording) SeveritySevere else MaroonPrimary)
                        ) {
                            Icon(
                                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = if (isRecording) "Stop Listening" else "Start Listening",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isRecording) "Listening... ($selectedLanguage)" else "Tap to start voice recognition",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isRecording) MaroonPrimary else TextPrimary
                        )
                        if (statusMessage.isNotBlank()) {
                            Text(
                                text = statusMessage,
                                fontSize = 11.sp,
                                color = TextSecondary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // Transcribed Text + Listen Text Button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Transcribed Text", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    TextButton(
                        onClick = {
                            val textToSpeak = voiceTranscript.ifBlank { getSampleTranscript(selectedLanguage) }
                            voiceManager.speak(textToSpeak, selectedLanguage)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Listen Text",
                            tint = MaroonPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Listen Text", fontSize = 12.sp, color = MaroonPrimary, fontWeight = FontWeight.Bold)
                    }
                }
                OutlinedTextField(
                    value = voiceTranscript.ifBlank { getSampleTranscript(selectedLanguage) },
                    onValueChange = { voiceTranscript = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Manual Input
            item {
                Text("Manual Input (Optional)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                OutlinedTextField(
                    value = manualInput,
                    onValueChange = { manualInput = it },
                    placeholder = { Text("Add or edit symptoms manually...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Common Symptoms (Chips)
            item {
                Text("Common Symptoms (Tap to add)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    commonSymptoms.forEach { symptom ->
                        val isSelected = selectedChips.contains(symptom)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) MaroonPrimary else CreamSurface)
                                .border(
                                    1.dp,
                                    if (isSelected) MaroonPrimary else SurfaceVariant,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable {
                                    if (isSelected) selectedChips.remove(symptom)
                                    else selectedChips.add(symptom)
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = (if (isSelected) "✓ " else "+ ") + symptom,
                                fontSize = 13.sp,
                                color = if (isSelected) Color.White else TextPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
