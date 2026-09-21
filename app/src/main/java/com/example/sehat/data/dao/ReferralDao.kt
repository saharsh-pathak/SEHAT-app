package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.Referral
import kotlinx.coroutines.flow.Flow

@Dao
interface ReferralDao {
    @Query("SELECT * FROM referrals WHERE episodeId = :episodeId LIMIT 1")
    fun getForEpisode(episodeId: Long): Flow<Referral?>

    @Query("SELECT * FROM referrals WHERE referralId = :referralId LIMIT 1")
    suspend fun getById(referralId: Long): Referral?

    @Query("SELECT * FROM referrals ORDER BY createdAt DESC")
    fun getAllReferrals(): Flow<List<Referral>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(referral: Referral): Long
}
