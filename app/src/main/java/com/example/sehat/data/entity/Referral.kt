package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "referrals")
data class Referral(
    @PrimaryKey(autoGenerate = true) val referralId: Long = 0,
    val episodeId: Long,
    val patientAbhaId: String,
    val destinationFacility: String,
    val priority: String, // Routine, Urgent, Emergency
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
