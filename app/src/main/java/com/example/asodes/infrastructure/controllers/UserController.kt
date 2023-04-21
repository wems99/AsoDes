package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.services.CreateClientService
import com.example.asodes.infrastructure.services.RetrieveClientByIdService
import com.example.asodes.infrastructure.services.RetrieveUserByIdService
import com.example.asodes.infrastructure.services.UpdateClientService
import com.example.asodes.infrastructure.services.UpdateUserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class UserController {
    companion object {
        @JvmStatic
        suspend fun retrieveUser(payload: Long): User? {
            val result = GlobalScope.async {
                RetrieveUserByIdService.perform(payload)
            }

            return result.await()
        }

        @JvmStatic
        suspend fun updateUser(payload: User): User? {
            val result = GlobalScope.async {
                UpdateUserService.perform(payload)
            }

            return result.await()
        }
    }
}