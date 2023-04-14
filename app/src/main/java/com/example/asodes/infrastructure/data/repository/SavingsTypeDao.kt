package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.SavingsType

@Dao
interface SavingsTypeDao {
    @Query("SELECT * FROM savings_types")
    suspend fun getAllSavingsTypes(): List<SavingsType>

    @Query("SELECT * FROM savings_types WHERE savings_type_id = :savingsTypeId")
    suspend fun getSavingsTypeById(savingsTypeId: Long): SavingsType?

    @Insert()
    suspend fun insertSavingsType(savingsType: SavingsType)

    @Update()
    suspend fun updateSavingsType(savingsType: SavingsType)

    @Delete
    suspend fun deleteSavingsType(savingsType: SavingsType)
}