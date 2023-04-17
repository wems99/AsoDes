package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.AuthenticationException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class AuthenticateUserService(private val db: SQLiteConnection) : BaseService<JSONObject, User> {
    override suspend fun execute(credentials: JSONObject): User? {
        val username = credentials.getString("username")
        val password = credentials.getString("password")
        val userDao = db.userDao()

        return userDao.getUserByUsernamePassword(username, password)
            ?: throw AuthenticationException("Invalid username or password")
    }

    companion object {
        @JvmStatic
       suspend fun perform(credentials: JSONObject): User? {
            val service = AuthenticateUserService(getDatabaseInstance())
            return service.execute(credentials)
        }
    }
}

