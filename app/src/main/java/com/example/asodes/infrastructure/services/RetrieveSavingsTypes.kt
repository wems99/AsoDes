package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.SavingsType
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveSavingsTypes(private val db: SQLiteConnection): BaseService<Any, List<SavingsType>> {
    override suspend fun execute(payload: Any): List<SavingsType> {
        val savingsTypeDao = db.savingsTypeDao()

        return savingsTypeDao.getAllSavingsTypes()
    }

    companion object {
        @JvmStatic
        suspend fun perform(): List<SavingsType> {
            val service = RetrieveSavingsTypes(getDatabaseInstance())
            return service.execute(0)
        }
    }
}