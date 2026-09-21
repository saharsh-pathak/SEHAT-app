package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "follow_up_tasks")
data class FollowUpTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val episodeId: Long,
    val patientAbhaId: String,
    val patientName: String,
    val village: String,
    val taskTitle: String,
    val dueDate: String,
    val status: String = "Pending", // Pending, Completed
    val checklistJson: String = "[]",
    val notes: String = ""
)
