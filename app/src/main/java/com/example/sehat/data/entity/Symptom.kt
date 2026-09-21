package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class Symptom(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val episodeId: Long,
    val voiceTranscript: String = "",
    val manualText: String = "",
    val selectedChips: String = "", // Comma-separated or JSON list
    val existingConditions: String = "",
    val currentMedicines: String = "",
    val allergies: String = "",
    val consentGiven: Boolean = true
)
