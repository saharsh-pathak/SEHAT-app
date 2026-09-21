package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey val abhaId: String,
    val aadhaarRef: String? = null,
    val name: String,
    val age: Int,
    val gender: String, // Male, Female, Other
    val village: String,
    val mobile: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
