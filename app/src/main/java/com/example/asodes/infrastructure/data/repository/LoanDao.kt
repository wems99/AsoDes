package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.Loan

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans")
    suspend fun getAllLoans(): List<Loan>

    @Query("SELECT * FROM loans WHERE loan_id = :loanId")
    suspend fun getLoanById(loanId: Long): Loan?

    @Query("SELECT * FROM loans WHERE client_id = :clientId")
    suspend fun getLoansByClientId(clientId: Long)

    @Insert()
    suspend fun insertLoan(loan: Loan)

    @Update()
    suspend fun updateLoan(loan: Loan)

    @Delete
    suspend fun deleteLoan(loan: Loan)
}