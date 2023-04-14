package com.example.asodes.infrastructure.services

import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.exceptions.NoUserFoundException

class RetrieveClientByIdService(private val db: SQLiteConnection) : BaseService<Long, Client> {
    override suspend fun execute(payload: Long): Client {
        val clientDao = db.clientDao()

        return clientDao.getClientByUserId(payload)
            ?: throw NoUserFoundException("Client not found")
    }

    companion object {
        @JvmStatic
        suspend fun execute(payload: Long): Client {
            val service = RetrieveClientByIdService(SQLiteConnection.getInstance(AsoDesUnidos.appContext))
            return service.execute(payload)
        }
    }
}