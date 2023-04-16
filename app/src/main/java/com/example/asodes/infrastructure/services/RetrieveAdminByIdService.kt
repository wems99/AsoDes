package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Admin
import com.example.asodes.infrastructure.exceptions.NoUserFoundException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RetrieveAdminByIdService(private val db: SQLiteConnection) : BaseService<Long, Admin> {
    override fun execute(payload: Long): Admin? {
        val adminDao = db.adminDao()

        return adminDao.getAdminByUserId(payload)
            ?: throw NoUserFoundException("Admin not found")
    }

    companion object {
        @JvmStatic
        fun perform(payload: Long): Admin? {
            val service = RetrieveAdminByIdService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}