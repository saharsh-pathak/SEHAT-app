package com.example.sehat.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_queue")
data class SyncQueue(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val entityType: String, // Patient, CareEpisode, Vitals, etc.
    val entityId: String,
    val action: String, // INSERT, UPDATE, DELETE
    val createdAt: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)
