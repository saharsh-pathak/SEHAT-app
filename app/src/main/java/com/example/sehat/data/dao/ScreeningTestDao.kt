package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.ScreeningTest
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreeningTestDao {
    @Query("SELECT * FROM screening_tests WHERE episodeId = :episodeId")
    fun getForEpisode(episodeId: Long): Flow<List<ScreeningTest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tests: List<ScreeningTest>)
}
