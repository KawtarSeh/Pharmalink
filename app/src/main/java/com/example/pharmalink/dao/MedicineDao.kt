package com.example.pharmalink.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pharmalink.models.Medicine

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines")
    suspend fun getAllMedicines(): List<Medicine>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine)

    @Query("DELETE FROM medicines")
    suspend fun deleteAllMedicines()

    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getMedicineById(id: Int): Medicine?

    @Query("SELECT * FROM medicines WHERE name LIKE :query LIMIT 1")
    suspend fun searchMedicineByName(query: String): Medicine?

}
