package com.example.sehat.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.sehat.ui.theme.*
import com.example.sehat.util.SpeechStreamingManager
import com.example.sehat.util.VoiceManager

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SymptomCollectionScreen(
    initialLanguage: String = "Marathi (मराठी)",
    initialLanguageCode: String = "mr",
    onNextClick: (transcript: String, manualText: String, selectedChips: List<String>) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val voiceManager = remember { VoiceManager(context) }
    val streamingManager = remember {
        SpeechStreamingManager(context, voiceManager, coroutineScope).apply {
            setLanguage(initialLanguageCode)
        }
    }

    val currentQIndex by streamingManager.currentQuestionIndex.collectAsState()
    val totalQ = streamingManager.totalQuestions
    val isSpeaking by streamingManager.isSpeaking.collectAsState()
    val isListening by streamingManager.isListening.collectAsState()

    val streamingQWords by streamingManager.streamingQuestionWords.collectAsState()
    val highlightedQIdx by streamingManager.highlightedQuestionWordIndex.collectAsState()

    val streamingTranscriptWords by streamingManager.streamingTranscriptWords.collectAsState()
    val highlightedTranscriptIdx by streamingManager.highlightedTranscriptWordIndex.collectAsState()
    val finalTranscript by streamingManager.finalTranscript.collectAsState()

    var manualNotes by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            streamingManager.toggleMicrophone()
        } else {
            Toast.makeText(context, "Microphone permission required for speech recognition", Toast.LENGTH_SHORT).show()
        }
    }

    fun requestMicOrToggle() {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            streamingManager.toggleMicrophone()
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // Start screening question on entry
    LaunchedEffect(Unit) {
        streamingManager.startScreening(initialQuestionIndex = 2) // Starts at Question 2 of 12 as per spec
    }

    DisposableEffect(Unit) {
        onDispose {
            streamingManager.stopScreening()
            voiceManager.destroy()
        }
    }

    // Pulse animation for mic
    val infiniteTransition = rememberInfiniteTransition(label = "micPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isListening || isSpeaking) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CreamBackground
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
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaroonPrimary
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Patient Screening",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaroonPrimary
                            )
                            Text(
                                text = "Step 1 of 4 • Voice Conversation",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextSecondary
                            )
                        }

                        // Language Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFBEBEB),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaroonPrimary.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2E7D32))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = initialLanguage,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaroonPrimary
                                )
                            }
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    streamingManager.stopScreening()
                                    Toast.makeText(context, "Screening paused", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.White,
                                    contentColor = TextPrimary
                                ),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF8C827A)),
                                modifier = Modifier
                                    .weight(0.4f)
                                    .height(50.dp)
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Stop", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }

                            Button(
                                onClick = {
                                    streamingManager.stopScreening()
                                    val outTranscript = finalTranscript.ifBlank {
                                        streamingTranscriptWords.joinToString(" ")
                                    }
                                    onNextClick(outTranscript, manualNotes, emptyList())
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaroonPrimary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(0.6f)
                                    .height(50.dp)
                            ) {
                                Text(
                                    text = "Continue to Vitals",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
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
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item { Spacer(modifier = Modifier.height(2.dp)) }

                // Speech Architecture Badges (IndicConformer & Kokoro)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFE8F0FE),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.RecordVoiceOver,
                                    contentDescription = null,
                                    tint = Color(0xFF1D4ED8),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text("Offline STT", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
                                    Text("IndicConformer", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFCE8E6),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFCA5A5)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = null,
                                    tint = MaroonPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text("Offline TTS", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
                                    Text("Kokoro Voice", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaroonPrimary)
                                }
                            }
                        }
                    }
                }

                // Conversation Progress Card (Question 2 of 12)
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE7E0D8)),
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
                                    text = "Question $currentQIndex of $totalQ",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaroonPrimary
                                )
                                Text(
                                    text = "${((currentQIndex.toFloat() / totalQ) * 100).toInt()}% Complete",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { currentQIndex.toFloat() / totalQ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = MaroonPrimary,
                                trackColor = Color(0xFFEDE7DD)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Spoken Question with Token-by-Token Progressive Streaming
                            Row(verticalAlignment = Alignment.Top) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(if (isSpeaking) MaroonPrimary else Color(0xFFF0EAE1)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = null,
                                        tint = if (isSpeaking) Color.White else MaroonPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        text = if (isSpeaking) "Kokoro Speaking..." else "Follow-up Question:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaroonPrimary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Streaming Question Tokens Flow
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        if (streamingQWords.isEmpty()) {
                                            Text(
                                                text = streamingManager.fullSpokenQuestionText.collectAsState().value,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = TextPrimary
                                            )
                                        } else {
                                            streamingQWords.forEachIndexed { idx, word ->
                                                val isHighlighted = idx == highlightedQIdx
                                                Text(
                                                    text = word,
                                                    fontSize = 15.sp,
                                                    fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
                                                    color = if (isHighlighted) MaroonPrimary else TextPrimary,
                                                    modifier = if (isHighlighted) {
                                                        Modifier
                                                            .background(Color(0xFFFDE8E8), RoundedCornerShape(4.dp))
                                                            .padding(horizontal = 3.dp)
                                                    } else Modifier
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Centered Large Microphone Button
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .scale(pulseScale),
                            contentAlignment = Alignment.Center
                        ) {
                            // Outer pulse ring
                            if (isListening || isSpeaking) {
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isListening) Color(0xFFDC2626).copy(alpha = 0.2f)
                                            else MaroonPrimary.copy(alpha = 0.2f)
                                        )
                                )
                            }

                            IconButton(
                                onClick = { requestMicOrToggle() },
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isListening -> Color(0xFFDC2626)
                                            isSpeaking -> MaroonPrimary
                                            else -> MaroonPrimary
                                        }
                                    )
                            ) {
                                Icon(
                                    imageVector = when {
                                        isListening -> Icons.Default.Mic
                                        isSpeaking -> Icons.AutoMirrored.Filled.VolumeUp
                                        else -> Icons.Default.Mic
                                    },
                                    contentDescription = "Microphone",
                                    tint = Color.White,
                                    modifier = Modifier.size(38.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = when {
                                isListening -> "Listening to patient... (Live STT)"
                                isSpeaking -> "Speaking question... (Kokoro TTS)"
                                else -> "Tap microphone to speak"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                isListening -> Color(0xFFDC2626)
                                isSpeaking -> MaroonPrimary
                                else -> TextPrimary
                            }
                        )
                    }
                }

                // Voice Control Secondary Buttons: Repeat, Skip (Solid White BG, Crisp High Contrast Text)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { streamingManager.repeatQuestion() },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = MaroonPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonPrimary),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = MaroonPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Repeat Question",
                                color = MaroonPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = { streamingManager.skipQuestion() },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = TextPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF57534E)),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = null,
                                tint = TextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Skip Question",
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Live Streaming STT Transcription Card (Goes as it is, NO Edit button)
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isListening) Color(0xFFDC2626).copy(alpha = 0.6f) else Color(0xFFE7E0D8)
                        ),
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(if (isListening) Color(0xFFDC2626) else Color(0xFF15803D))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isListening) "Listening (Live STT)..." else "Patient Response Transcript",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Live token by token streaming output (no edit button, transcript goes as it is)
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (streamingTranscriptWords.isEmpty()) {
                                    Text(
                                        text = "Patient voice transcript will appear here as spoken...",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextSecondary,
                                        lineHeight = 18.sp
                                    )
                                } else {
                                    streamingTranscriptWords.forEachIndexed { idx, word ->
                                        val isHighlighted = idx == highlightedTranscriptIdx
                                        Text(
                                            text = word,
                                            fontSize = 14.sp,
                                            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
                                            color = if (isHighlighted) Color(0xFFDC2626) else TextPrimary,
                                            modifier = if (isHighlighted) {
                                                Modifier
                                                    .background(Color(0xFFFDE8E8), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 2.dp)
                                            } else Modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Green Informational Card
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFDCFCE7),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC)),
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
                                    .background(Color(0xFFBBF7D0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF15803D),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Screening automatically ends once all required clinical questions have been completed.",
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF14532D)
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(10.dp)) }
            }
        }
    }
}
