package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Admin
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.BackgroundRunner
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateAdminService(private val db: SQLiteConnection) : BaseService<JSONObject, Admin> {
    override suspend fun execute(payload: JSONObject): Admin? {
        payload.put("isAdmin", true)
        val user = CreateUserService.perform(payload)
        val adminDao = db.adminDao()

        val admin = Admin.createAdmin(user!!.id)
        val adminId = adminDao.insertAdmin(admin)

        return adminDao.getAdminById(adminId)
            ?: throw UnableToCreateRecord("Error occurred while creating admin")
    }

    companion object {
        @JvmStatic
       suspend fun perform(payload: JSONObject): Admin? {
            val service = CreateAdminService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}

