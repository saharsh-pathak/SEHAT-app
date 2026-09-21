package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments WHERE referralId = :referralId LIMIT 1")
    fun getForReferral(referralId: Long): Flow<Appointment?>

    @Query("SELECT * FROM appointments WHERE appointmentId = :id LIMIT 1")
    suspend fun getById(id: Long): Appointment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment): Long
}
