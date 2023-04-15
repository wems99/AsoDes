package com.example.asodes.infrastructure.controllers

import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.services.AuthenticateUserService
import com.example.asodes.infrastructure.services.RetrieveAdminByIdService
import com.example.asodes.infrastructure.services.RetrieveClientByIdService
import org.json.JSONObject

class AuthController {
    companion object {
        @JvmStatic
        suspend fun authenticate(username: String, password: String): Any? {
            val payload = JSONObject()
            payload.put("username", username)
            payload.put("password", password)

            val user = AuthenticateUserService.perform(payload)

           if (user!!.isAdmin) {
               return RetrieveAdminByIdService.perform(user!!.id)
           }

            return RetrieveClientByIdService.perform(user!!.id)
        }
    }
}