package com.example.asodes.infrastructure.services

import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Admin
import com.example.asodes.infrastructure.exceptions.NoUserFoundException

class RetrieveAdminByIdService(private val db: SQLiteConnection) : BaseService<Long, Admin> {
    override suspend fun execute(payload: Long): Admin {
        val adminDao = db.adminDao()

        return adminDao.getAdminByUserId(payload)
            ?: throw NoUserFoundException("Admin not found")
    }

    companion object {
        @JvmStatic
        suspend fun execute(payload: Long): Admin {
            val service = RetrieveAdminByIdService(SQLiteConnection.getInstance(AsoDesUnidos.appContext))
            return service.execute(payload)
        }
    }
}