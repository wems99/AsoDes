package com.example.asodes.infrastructure.services

import android.util.Log
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class UpdateLoanService(private val db: SQLiteConnection): BaseService<Loan, Loan> {
    override suspend fun execute(payload: Loan): Loan {
        val creditType = db.creditTypeDao().getCreditTypeById(payload.creditTypeId)
        val clientSalary = payload.clientSalary
        val percentage = payload.percentage
        val feePercentage = creditType!!.percentage
        val contributions = payload.contributions
        val total = clientSalary * (percentage / 100.0)
        val fee = total * (feePercentage / 100.0)
        val totalWithFee = total + fee
        val totalLeft =  totalWithFee - contributions

        if (totalLeft <= 0.0) {
            db.loanDao().deleteLoan(payload)
        } else {
            db.loanDao().updateLoan(payload)
        }

        return payload
    }

    companion object {
        @JvmStatic
        suspend fun perform(loan: Loan): Loan {
            val service = UpdateLoanService(getDatabaseInstance())
            return service.execute(loan)
        }
    }
}