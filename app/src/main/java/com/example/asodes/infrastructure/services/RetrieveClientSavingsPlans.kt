package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.utils.getDatabaseInstance

class RetrieveClientSavingsPlans(private val db: SQLiteConnection): BaseService<Long, List<SavingsPlan>> {
    override suspend fun execute(payload: Long): List<SavingsPlan> {
        val savingsPlansDao = db.savingsPlanDao()
        return savingsPlansDao.getSavingsPlansByClientId(payload)
    }

    companion object {
        @JvmStatic
        suspend fun perform(clientId: Long): List<SavingsPlan> {
            val service = RetrieveClientSavingsPlans(getDatabaseInstance())
            return service.execute(clientId)
        }
    }
}