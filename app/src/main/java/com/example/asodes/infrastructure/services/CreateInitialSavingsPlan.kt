package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateInitialSavingsPlan(private val db: SQLiteConnection): BaseService<JSONObject, Unit> {
    override suspend fun execute(payload: JSONObject): Unit {
        val savingsPlanDao = db.savingsPlanDao()
        val clientId = payload.getLong("clientId")
        val savingsTypeId = payload.getLong("savingsPlanId")
        val savingsPlan = SavingsPlan.createSavingsPlan(clientId, savingsTypeId, 0.0)

        savingsPlanDao.insertSavingsPlan(savingsPlan)
    }

    companion object {
        @JvmStatic
        suspend fun perform(payload: JSONObject): Unit {
            val service = CreateInitialSavingsPlan(getDatabaseInstance())
            service.execute(payload)
        }
    }
}