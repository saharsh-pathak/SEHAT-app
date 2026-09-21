package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.SyncQueue
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncQueueDao {
    @Query("SELECT * FROM sync_queue WHERE synced = 0")
    fun getUnsynced(): Flow<List<SyncQueue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SyncQueue)

    @Query("UPDATE sync_queue SET synced = 1 WHERE id = :id")
    suspend fun markSynced(id: Long)
}
