package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Admin
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveAdminByIdService(private val db: SQLiteConnection) : BaseService<Long, Admin> {
    override suspend fun execute(payload: Long): Admin? {
        val adminDao = db.adminDao()

        return adminDao.getAdminByUserId(payload)
            ?: throw NoRecordFoundException("Admin not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(adminId: Long): Admin? {
            val service = RetrieveAdminByIdService(getDatabaseInstance())
            return service.execute(adminId)
        }
    }
}