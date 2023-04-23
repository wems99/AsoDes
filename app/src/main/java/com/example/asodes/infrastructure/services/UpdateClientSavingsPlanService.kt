package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.MIN_AMOUNT
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.exceptions.NotEnoughSalary
import com.example.asodes.infrastructure.exceptions.SavingsPlanMinAmountException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class UpdateClientSavingsPlanService(private val db: SQLiteConnection): BaseService<SavingsPlan, SavingsPlan> {
    override suspend fun execute(payload: SavingsPlan): SavingsPlan? {
        val savingsPlanDao = db.savingsPlanDao()
        val loansDao = db.loanDao()
        val creditTypeDao = db.creditTypeDao()

        if (payload.amount < MIN_AMOUNT) {
            throw SavingsPlanMinAmountException("Amount must be 5000 minimum")
        }

        val clientId = payload.clientId
        val loans = loansDao.getLoansByClientId(clientId)
        val savings = savingsPlanDao.getSavingsPlansByClientId(clientId)
        var loanAmount = 0.0
        var savingsAmount = 0.0
        for (loan in loans) {
            val creditType = creditTypeDao.getCreditTypeById(loan.creditTypeId)
            loanAmount += loan.calculateLoanAmount() * (creditType!!.percentage / 100)
        }
        for (saving in savings) {
            if (saving.savingsTypeId != payload.savingsTypeId && saving.active) {
                savingsAmount += saving.amount
            }
        }
        var totalMonth = loanAmount + savingsAmount
        val client = RetrieveClientByIdService.perform(clientId)

        if (client!!.salary < (totalMonth + payload.amount) && payload.active) {
            throw NotEnoughSalary("Salary is not enough")
        }

        savingsPlanDao.updateSavingsPlan(payload)
        val savingsPlan = savingsPlanDao.getClientSavingsPlan(payload.clientId, payload.savingsTypeId)

        return savingsPlan ?: throw NoRecordFoundException("Savings plan not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(payload: SavingsPlan): SavingsPlan? {
            val service = UpdateClientSavingsPlanService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}