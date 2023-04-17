package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.BackgroundRunner
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateUserService(private val db: SQLiteConnection) : BaseService<JSONObject, User> {
    override suspend fun execute(payload: JSONObject): User? {
        val user = User.fromJson(payload)
        val useDao = db.userDao()

        val userId = useDao.insertUser(user)

        return useDao.getUserById(userId)
            ?: throw UnableToCreateRecord("Error occurred while creating user")
    }

    companion object {
        @JvmStatic
       suspend fun perform(payload: JSONObject): User? {
            val service = CreateUserService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}

