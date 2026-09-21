package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val appointmentId: Long = 0,
    val appointmentCode: String, // APT250917001
    val referralId: Long,
    val patientAbhaId: String,
    val facility: String,
    val dateTime: String,
    val consultationType: String = "General Consultation",
    val queueToken: String = "Token #12",
    val createdAt: Long = System.currentTimeMillis()
)
