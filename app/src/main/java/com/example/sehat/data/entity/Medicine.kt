package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val form: String, // Tablet, Capsule, Sachet, Syrup
    val facility: String,
    val stockCount: Int,
    val status: String // In Stock, Low Stock, Unavailable
)
