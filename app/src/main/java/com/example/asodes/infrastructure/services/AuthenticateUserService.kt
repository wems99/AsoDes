package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.AuthenticationException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class AuthenticateUserService(private val db: SQLiteConnection) : BaseService<JSONObject, User> {
    override fun execute(payload: JSONObject): User? {
        val username = payload.getString("username")
        val password = payload.getString("password")
        val userDao = db.userDao()

        return userDao.getUserByUsernamePassword(username, password)
            ?: throw AuthenticationException("Invalid username or password")
    }

    companion object {
        @JvmStatic
        fun perform(payload: JSONObject): User? {
            val service = AuthenticateUserService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}

