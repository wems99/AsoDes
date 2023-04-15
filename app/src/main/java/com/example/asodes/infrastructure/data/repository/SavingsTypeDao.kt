package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.data.local.entity.SavingsType

@Dao
interface SavingsTypeDao {
    @Query("SELECT * FROM savings_types")
    fun getAllSavingsTypes(): List<SavingsType>

    @Query("SELECT * FROM savings_types WHERE id = :savingsTypeId")
    fun getSavingsTypeById(savingsTypeId: Long): SavingsType?

    @Insert
    fun insertSavingsType(savingsType: SavingsType)

    @Update
    fun updateSavingsType(savingsType: SavingsType)

    @Delete
    fun deleteSavingsType(savingsType: SavingsType)
}