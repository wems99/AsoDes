package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.AuthenticationException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class AuthenticateUserService(private val db: SQLiteConnection) : BaseService<String, User> {
    override suspend fun execute(payload: String): User {
        val credentials = JSONObject(payload)
        val username = credentials.getString("username")
        val password = credentials.getString("password")
        val userDao = db.userDao()

        return userDao.getUserByUsernamePassword(username, password)
            ?: throw AuthenticationException("Invalid username or password")
    }

    companion object {
        @JvmStatic
        suspend fun execute(payload: String): User {
            val service = AuthenticateUserService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}

