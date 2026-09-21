package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.Symptom
import kotlinx.coroutines.flow.Flow

@Dao
interface SymptomDao {
    @Query("SELECT * FROM symptoms WHERE episodeId = :episodeId LIMIT 1")
    fun getForEpisode(episodeId: Long): Flow<Symptom?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(symptom: Symptom): Long
}
