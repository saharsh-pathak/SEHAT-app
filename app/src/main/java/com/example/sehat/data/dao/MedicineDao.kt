package com.example.sehat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sehat.data.entity.Medicine
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines WHERE stockCount > 0 GROUP BY name ORDER BY name ASC")
    fun getAllMedicines(): Flow<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE stockCount > 0 AND (name LIKE '%' || :query || '%' OR form LIKE '%' || :query || '%' OR facility LIKE '%' || :query || '%') GROUP BY name ORDER BY name ASC")
    fun searchMedicines(query: String): Flow<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE name = :medicineName ORDER BY stockCount DESC")
    fun getFacilitiesForMedicine(medicineName: String): Flow<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE status = :status")
    fun filterByStatus(status: String): Flow<List<Medicine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicines: List<Medicine>)
}
