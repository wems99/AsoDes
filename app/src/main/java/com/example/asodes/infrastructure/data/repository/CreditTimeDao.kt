package com.example.asodes.infrastructure.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.CreditTime

interface CreditTimeDao {
    @Query("SELECT * FROM credit_times")
    suspend fun getAllCreditTimes(): List<CreditTime>

    @Query("SELECT * FROM credit_times WHERE credit_time_id = :creditTimeId")
    suspend fun getCreditTimeById(creditTimeId: Long): CreditTime?

    @Insert()
    suspend fun insertCreditTime(creditTime: CreditTime)

    @Update()
    suspend fun updateCreditTime(creditTime: CreditTime)

    @Delete
    suspend fun deleteCreditTime(creditTime: CreditTime)
}