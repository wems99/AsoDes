package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.exceptions.NoUserFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveClientByIdService(private val db: SQLiteConnection) : BaseService<Long, Client> {
    override suspend fun execute(payload: Long): Client {
        val clientDao = db.clientDao()

        return clientDao.getClientByUserId(payload)
            ?: throw NoUserFoundException("Client not found")
    }

    companion object {
        @JvmStatic
        suspend fun execute(payload: Long): Client {
            val service = RetrieveClientByIdService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}