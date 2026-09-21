package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.CareEpisode
import kotlinx.coroutines.flow.Flow

@Dao
interface CareEpisodeDao {
    @Query("SELECT * FROM care_episodes WHERE patientAbhaId = :abhaId ORDER BY createdAt DESC")
    fun getEpisodesForPatient(abhaId: String): Flow<List<CareEpisode>>

    @Query("SELECT * FROM care_episodes WHERE episodeId = :episodeId LIMIT 1")
    suspend fun getById(episodeId: Long): CareEpisode?

    @Query("SELECT * FROM care_episodes WHERE status = 'OPEN'")
    fun getOpenEpisodes(): Flow<List<CareEpisode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: CareEpisode): Long
}
