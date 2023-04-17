package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class UpdateClientService(private val db: SQLiteConnection): BaseService<Client, Client> {
    override suspend fun execute(payload: Client): Client {
        db.clientDao().updateClient(payload)
        return payload
    }

    companion object {
        @JvmStatic
        suspend fun perform(client: Client): Client {
            val service = UpdateClientService(getDatabaseInstance())
            return service.execute(client)
        }
    }
}