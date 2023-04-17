package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan

class RetrieveClientFreeAmount(private val db: SQLiteConnection): BaseService<Long, Double> {
    override suspend fun execute(payload: Long): Double {
        val savingsPlans = RetrieveClientSavingsPlans.perform(payload)
        val loans = RetrieveClientLoansService.perform(payload)
        val client = RetrieveClientByIdService.perform(payload)

        return client!!.salary - calculateSavingsPlansAmount(savingsPlans) - calculateLoansAmount(loans)
    }

    private fun calculateSavingsPlansAmount(savingsPlans: List<SavingsPlan>): Double {
       return savingsPlans.sumOf { it.amount }
    }

    private fun calculateLoansAmount(loans: List<Loan>): Double {
        return loans.sumOf { loan ->
            val creditTypeId = loan.creditTypeId
            val creditType = db.creditTypeDao().getCreditTypeById(creditTypeId)
            loan.clientSalary * (creditType!!.percentage / 100)
        }
    }

}