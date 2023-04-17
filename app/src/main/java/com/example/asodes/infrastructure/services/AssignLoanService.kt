package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class AssignLoanService(private val db: SQLiteConnection) : BaseService<JSONObject, Loan> {
    override suspend fun execute(payload: JSONObject): Loan? {
        val loan = Loan.fromJson(payload)
        val loanDao = db.loanDao()

        val loanId = loanDao.insertLoan(loan)

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