package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveCreditTimeById(private val db: SQLiteConnection) : BaseService<Long, CreditTime> {
    override suspend fun execute(payload: Long): CreditTime? {
        val creditTimeDao = db.creditTimeDao()

        return creditTimeDao.getCreditTimeById(payload)
            ?: throw NoRecordFoundException("User not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(creditTimeId: Long): CreditTime? {
            val service = RetrieveCreditTimeById(getDatabaseInstance())
            return service.execute(creditTimeId)
        }
    }
}