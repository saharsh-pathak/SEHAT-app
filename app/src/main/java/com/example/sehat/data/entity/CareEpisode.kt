package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "care_episodes")
data class CareEpisode(
    @PrimaryKey(autoGenerate = true) val episodeId: Long = 0,
    val patientAbhaId: String,
    val facilityName: String,
    val status: String = "OPEN", // OPEN, CLOSED, REOPENED
    val createdAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null
)
