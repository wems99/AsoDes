package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.data.local.entity.Loan

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans")
    fun getAllLoans(): List<Loan>

    @Query("SELECT * FROM loans WHERE id = :loanId")
    fun getLoanById(loanId: Long): Loan?

    @Query("SELECT * FROM loans WHERE loan_client_id = :clientId")
    fun getLoansByClientId(clientId: Long): List<Loan>

    @Insert
    fun insertLoan(loan: Loan)

    @Update
    fun updateLoan(loan: Loan)

    @Delete
    fun deleteLoan(loan: Loan)
}