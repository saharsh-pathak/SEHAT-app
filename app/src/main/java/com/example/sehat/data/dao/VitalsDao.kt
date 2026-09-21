package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.Vitals
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {
    @Query("SELECT * FROM vitals WHERE episodeId = :episodeId LIMIT 1")
    fun getForEpisode(episodeId: Long): Flow<Vitals?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vitals: Vitals): Long
}
