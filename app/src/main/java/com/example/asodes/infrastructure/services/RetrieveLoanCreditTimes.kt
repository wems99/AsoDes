package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveLoanCreditTimes(private val db: SQLiteConnection) : BaseService<Any, List<CreditTime>> {
    override suspend fun execute(payload: Any): List<CreditTime> {
        val creditTimeDao = db.creditTimeDao()

        return creditTimeDao.getAllCreditTimes()
    }

    companion object {
        @JvmStatic
        suspend fun perform(): List<CreditTime> {
            val service = RetrieveLoanCreditTimes(getDatabaseInstance())
            return service.execute(0)
        }
    }
}