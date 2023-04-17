package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveLoanCreditTypes(private val db: SQLiteConnection) : BaseService<Any, List<CreditType>> {
    override suspend fun execute(payload: Any): List<CreditType> {
        val creditType = db.creditTypeDao()

        return creditType.getAllCreditTypes()
    }

    companion object {
        @JvmStatic
        suspend fun perform(): List<CreditType> {
            val service = RetrieveLoanCreditTypes(getDatabaseInstance())
            return service.execute(0)
        }
    }
}