package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.TriageResult
import kotlinx.coroutines.flow.Flow

@Dao
interface TriageResultDao {
    @Query("SELECT * FROM triage_results WHERE episodeId = :episodeId LIMIT 1")
    fun getForEpisode(episodeId: Long): Flow<TriageResult?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(triageResult: TriageResult): Long
}
