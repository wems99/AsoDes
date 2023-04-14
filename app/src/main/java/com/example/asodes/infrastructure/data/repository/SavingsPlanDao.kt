package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan

@Dao
interface SavingsPlanDao {
    @Query("SELECT * FROM savings_plans")
    suspend fun getAllSavingsPlans(): List<SavingsPlan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingsPlan(savingType: SavingsPlan)

    @Query("SELECT * FROM savings_plans WHERE client_id = :clientId AND savings_type_id = :savingsTypeId")
    suspend fun getClientSavingsPlan(clientId: Int, savingsTypeId: Int): SavingsPlan?

    @Query("DELETE FROM savings_plans WHERE client_id = :clientId AND savings_type_id = :savingsTypeId")
    suspend fun deleteSavingsPlan(clientId: Int, savingsTypeId: Int)

    @Update()
    suspend fun updateSavingsPlan(savingType: SavingsPlan)
}