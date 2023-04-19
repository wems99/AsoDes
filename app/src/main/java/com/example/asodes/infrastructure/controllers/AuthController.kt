package com.example.asodes.infrastructure.controllers


import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.services.AuthenticateUserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class AuthController {
    companion object {
        @JvmStatic
        suspend fun authenticate(payload: JSONObject): User? {
            val result = GlobalScope.async {
                AuthenticateUserService.perform(payload)
            }

            return result.await()
        }
    }
}