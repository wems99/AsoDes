package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveCreditTypeById(private val db: SQLiteConnection) : BaseService<Long, CreditType> {
    override suspend fun execute(payload: Long): CreditType? {
        val creditTypeDao = db.creditTypeDao()

        return creditTypeDao.getCreditTypeById(payload)
            ?: throw NoRecordFoundException("User not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(creditTimeId: Long): CreditType? {
            val service = RetrieveCreditTypeById(getDatabaseInstance())
            return service.execute(creditTimeId)
        }
    }
}