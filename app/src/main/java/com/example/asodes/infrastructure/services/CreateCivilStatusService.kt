package com.example.asodes.infrastructure.services

import android.util.Log
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class CreateCivilStatusService(private val db: SQLiteConnection) : BaseService<String, CivilStatus> {
    override fun execute(payload: String): CivilStatus? {
        val civilStatusDao = db.civilStatusDao()
        val civilStatus = CivilStatus(
            name = payload
        )

       val civilStatusId: Long = civilStatusDao.insertCivilStatus(civilStatus)
        civilStatusDao.getCivilStatusById(civilStatusId)?.let { Log.d("Civil status:", it.name) }

        return civilStatusDao.getCivilStatusById(civilStatusId)
            ?: throw UnableToCreateRecord("Error while creating civil status")
    }

    companion object {
        @JvmStatic
        fun perform(payload: String): CivilStatus? {
            val service = CreateCivilStatusService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}