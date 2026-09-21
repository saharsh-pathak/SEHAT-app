package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "screening_tests")
data class ScreeningTest(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val episodeId: Long,
    val testName: String, // Pregnancy, Malaria, Dengue, Urine, TB, Other
    val result: String // Positive, Negative, Pending
)
