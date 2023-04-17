package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveClientLoansService(private val db: SQLiteConnection) : BaseService<Long, List<Loan>> {
    override suspend fun execute(payload: Long): List<Loan> {
        val loanDao = db.loanDao()

        return loanDao.getLoansByClientId(payload)
    }
    companion object {
        @JvmStatic
        suspend fun perform(clientId: Long): List<Loan> {
            val service = RetrieveClientLoansService(getDatabaseInstance())
            return service.execute(clientId)
        }
    }
}