package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.CreditTime

@Dao
interface CreditTimeDao {
    @Query("SELECT * FROM credit_times")
    fun getAllCreditTimes(): List<CreditTime>

    @Query("SELECT * FROM credit_times WHERE id = :creditTimeId")
    fun getCreditTimeById(creditTimeId: Long): CreditTime?

    @Insert
    fun insertCreditTime(creditTime: CreditTime)

    @Update
    fun updateCreditTime(creditTime: CreditTime)

    @Delete
    fun deleteCreditTime(creditTime: CreditTime)
}