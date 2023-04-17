package com.example.asodes.infrastructure.services

import android.util.Log
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class CreateCivilStatusService(private val db: SQLiteConnection) : BaseService<String, CivilStatus> {
    override suspend fun execute(civilStatusName: String): CivilStatus? {
        val civilStatusDao = db.civilStatusDao()
        val civilStatus = CivilStatus(
            name = civilStatusName
        )

       val civilStatusId: Long = civilStatusDao.insertCivilStatus(civilStatus)

        return civilStatusDao.getCivilStatusById(civilStatusId)
            ?: throw UnableToCreateRecord("Error while creating a civil status")
    }

    companion object {
        @JvmStatic
        suspend fun perform(civilStatusName: String): CivilStatus? {
            val service = CreateCivilStatusService(getDatabaseInstance())
            return service.execute(civilStatusName)
        }
    }
}