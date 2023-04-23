package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.data.local.entity.MIN_AMOUNT
import com.example.asodes.infrastructure.exceptions.NotEnoughSalary
import com.example.asodes.infrastructure.exceptions.SavingsPlanMinAmountException
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class AssignLoanService(private val db: SQLiteConnection) : BaseService<JSONObject, Loan> {
    override suspend fun execute(payload: JSONObject): Loan? {
        val newLoan = Loan.fromJson(payload)
        val loanDao = db.loanDao()
        val savingsPlanDao = db.savingsPlanDao()
        val creditTypeDao = db.creditTypeDao()

        val clientId = payload.getLong("clientId")
        val loans = loanDao.getLoansByClientId(clientId)
        val savings = savingsPlanDao.getSavingsPlansByClientId(clientId)
        var loanAmount = 0.0
        var savingsAmount = 0.0
        for (loan in loans) {
            val creditType = creditTypeDao.getCreditTypeById(loan.creditTypeId)
            loanAmount += loan.calculateLoanAmount() * (creditType!!.percentage / 100)
        }
        for (saving in savings) {
            if (saving.active) {
                savingsAmount += saving.amount
            }
        }
        var totalMonth = loanAmount + savingsAmount
        val client = RetrieveClientByIdService.perform(clientId)
        val creditType = creditTypeDao.getCreditTypeById(newLoan.creditTypeId)
        val newLoanAmount = newLoan.calculateLoanAmount() * (creditType!!.percentage / 100)
        if (client!!.salary < (totalMonth + newLoanAmount)) {
            throw NotEnoughSalary("Salary is not enough")
        }
        val loanId = loanDao.insertLoan(newLoan)

        return loanDao.getLoanById(loanId)
            ?: throw UnableToCreateRecord("Unable to create loan")
    }

    companion object {
        @JvmStatic
        suspend fun perform(payload: JSONObject): Loan? {
            val service = AssignLoanService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}