package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "triage_results")
data class TriageResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val episodeId: Long,
    val probableCondition: String,
    val severity: String, // Mild, Moderate, Severe, Emergency
    val recommendedFacility: String,
    val recommendedProfessional: String,
    val justification: String,
    val clinicalNotes: String = ""
)
