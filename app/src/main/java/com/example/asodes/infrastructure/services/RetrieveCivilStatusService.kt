package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveCivilStatusService(private val db: SQLiteConnection) : BaseService<Any, List<CivilStatus>> {
    override suspend fun execute(payload: Any): List<CivilStatus> {
        val civilStatusDao = db.civilStatusDao()

        return civilStatusDao.getAllCivilStatuses()
    }

    companion object {
        @JvmStatic
        suspend fun perform(): List<CivilStatus> {
            val service = RetrieveCivilStatusService(getDatabaseInstance())
            return service.execute(0)
        }
    }
}