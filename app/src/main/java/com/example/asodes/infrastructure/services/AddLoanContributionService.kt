package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.exceptions.LoadContributionException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class AddLoanContributionService(private val db: SQLiteConnection): BaseService<JSONObject, Unit> {
    override suspend fun execute(payload: JSONObject) {
        val loanId = payload.getLong("loanId")
        val amount = payload.getDouble("amount")

        val loan = db.loanDao().getLoanById(loanId)

        if (loan!!.calculateLeftLoanAmount() > amount) {
            throw LoadContributionException("Amount to contribute exceeds what is left")
        }

        loan.addContribution(amount)
        db.loanDao().updateLoan(loan)
    }

    companion object {
        @JvmStatic
        suspend fun perform(payload: JSONObject) {
            val service = AddLoanContributionService(getDatabaseInstance())
            service.execute(payload)
        }
    }
}