package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class UpdateUserService(private val db: SQLiteConnection): BaseService<User, User> {
    override suspend fun execute(payload: User): User {
        db.userDao().updateUser(payload)
        return payload
    }

    companion object {
        @JvmStatic
        suspend fun perform(user: User): User {
            val service = UpdateUserService(getDatabaseInstance())
            return service.execute(user)
        }
    }
}