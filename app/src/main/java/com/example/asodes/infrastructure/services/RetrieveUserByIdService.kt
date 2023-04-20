package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveUserByIdService(private val db: SQLiteConnection) : BaseService<Long, User> {
    override suspend fun execute(payload: Long): User? {
        val userDao = db.userDao()

        return userDao.getUserById(payload)
            ?: throw NoRecordFoundException("User not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(clientId: Long): User? {
            val service = RetrieveUserByIdService(getDatabaseInstance())
            return service.execute(clientId)
        }
    }
}