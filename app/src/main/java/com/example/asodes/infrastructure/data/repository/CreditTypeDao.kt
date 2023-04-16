package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.CreditType

@Dao
interface CreditTypeDao {
    @Query("SELECT * FROM credit_types")
    fun getAllCreditTypes(): List<CreditType>

    @Query("SELECT * FROM credit_types WHERE id = :creditTypeId")
    fun getCreditTypeById(creditTypeId: Long): CreditType?

    @Insert
    fun insertCreditType(creditType: CreditType)

    @Update
    fun updateCreditType(creditType: CreditType)

    @Delete
    fun deleteCreditType(creditType: CreditType)
}