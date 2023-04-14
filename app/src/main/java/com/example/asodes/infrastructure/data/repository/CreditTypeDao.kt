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
    suspend fun getAllCreditTypes(): List<CreditType>

    @Query("SELECT * FROM credit_types WHERE credit_type_id = :creditTypeId")
    suspend fun getCreditTypeById(creditTypeId: Long): CreditType?

    @Insert()
    suspend fun insertCreditType(creditType: CreditType)

    @Update()
    suspend fun updateCreditType(creditType: CreditType)

    @Delete
    suspend fun deleteCreditType(creditType: CreditType)
}