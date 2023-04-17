package com.example.asodes.infrastructure.controllers

import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.services.AuthenticateUserService
import com.example.asodes.infrastructure.services.RetrieveAdminByIdService
import com.example.asodes.infrastructure.services.RetrieveClientByIdService
import com.example.asodes.infrastructure.utils.BackgroundRunner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class AuthController {
    companion object {
        @JvmStatic
        suspend fun authenticate(payload: JSONObject): Any? {
            val result = GlobalScope.async {
                AuthenticateUserService.perform(payload)
            }

            return result.await()
        }
    }
}