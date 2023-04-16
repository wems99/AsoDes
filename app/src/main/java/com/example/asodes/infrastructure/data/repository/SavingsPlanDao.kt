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
    fun getAllSavingsPlans(): List<SavingsPlan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSavingsPlan(savingType: SavingsPlan)

    @Query("SELECT * FROM savings_plans WHERE savings_plan_client_id = :clientId AND savings_plan_savings_type_id = :savingsTypeId")
    fun getClientSavingsPlan(clientId: Long, savingsTypeId: Long): SavingsPlan?

    @Query("DELETE FROM savings_plans WHERE savings_plan_client_id = :clientId AND savings_plan_savings_type_id = :savingsTypeId")
    fun deleteSavingsPlan(clientId: Long, savingsTypeId: Long)

    @Update
    fun updateSavingsPlan(savingType: SavingsPlan)
}