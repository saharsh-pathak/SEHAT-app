package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class Vitals(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val episodeId: Long,
    val bloodPressure: String = "120/80",
    val heartRate: Int = 88,
    val spo2: Int = 98,
    val temperature: Float = 37.8f,
    val bloodGlucose: Int = 110,
    val hemoglobin: Float = 11.2f,
    val weight: Float = 58.0f,
    val height: Float = 162.0f,
    val clinicalNotes: String = ""
)
