package com.example.pharmalink.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pharmalink.models.Vitamin


@Dao
interface VitaminDao {
    @Query("SELECT * FROM vitamins")
    suspend fun getAllVitamins(): List<Vitamin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitamin(vitamin: Vitamin)

    @Query("DELETE FROM vitamins")
    suspend fun deleteAllMedicines()

    @Query("SELECT * FROM vitamins WHERE id = :id")
    suspend fun getVitaminById(id: Int): Vitamin?

    @Query("SELECT * FROM vitamins WHERE name LIKE :query LIMIT 1")
    suspend fun searchVitaminByName(query: String): Vitamin?


}
