package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.FollowUpTask
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowUpTaskDao {
    @Query("SELECT * FROM follow_up_tasks ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<FollowUpTask>>

    @Query("SELECT * FROM follow_up_tasks WHERE status = :status ORDER BY dueDate ASC")
    fun getTasksByStatus(status: String): Flow<List<FollowUpTask>>

    @Query("SELECT COUNT(*) FROM follow_up_tasks WHERE status = 'Pending'")
    fun getPendingCount(): Flow<Int>

    @Query("SELECT * FROM follow_up_tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): FollowUpTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: FollowUpTask): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<FollowUpTask>)

    @Query("UPDATE follow_up_tasks SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)
}
