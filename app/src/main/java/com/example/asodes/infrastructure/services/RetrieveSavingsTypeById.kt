package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.SavingsType
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveSavingsTypeById(private val db: SQLiteConnection) : BaseService<Long, SavingsType> {
    override suspend fun execute(payload: Long): SavingsType? {
        val savingsTypeDao = db.savingsTypeDao()

        return savingsTypeDao.getSavingsTypeById(payload)
            ?: throw NoRecordFoundException("Savings Type not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(savingsId: Long): SavingsType? {
            val service = RetrieveSavingsTypeById(getDatabaseInstance())
            return service.execute(savingsId)
        }
    }
}