package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.services.CreateClientService
import com.example.asodes.infrastructure.services.RetrieveClientByIdService
import com.example.asodes.infrastructure.services.UpdateClientService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class ClientController {
    companion object {
        @JvmStatic
        suspend fun createClient(payload: JSONObject): Client? {
            val result = GlobalScope.async {
                CreateClientService.perform(payload)
            }

            return result.await()
        }

        @JvmStatic
        suspend fun retrieveClient(clientId: Long): Client? {
            val result = GlobalScope.async {
                RetrieveClientByIdService.perform(clientId)
            }

            return result.await()
        }

        @JvmStatic
        suspend fun updateClient(client: Client): Client {
            val result = GlobalScope.async {
                UpdateClientService.perform(client)
            }

            return result.await()
        }
    }
}