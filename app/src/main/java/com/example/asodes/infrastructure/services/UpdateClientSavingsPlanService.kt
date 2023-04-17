package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.MIN_AMOUNT
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.exceptions.NoRecordFoundException
import com.example.asodes.infrastructure.exceptions.SavingsPlanMinAmountException
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class UpdateClientSavingsPlanService(private val db: SQLiteConnection): BaseService<JSONObject, SavingsPlan> {
    override suspend fun execute(payload: JSONObject): SavingsPlan? {
        val savingsTypeId =  payload.getLong("savingsTypeId")
        val clientId = payload.getLong("clientId")
        val amount = payload.getDouble("amount")
        val savingsPlanDao = db.savingsPlanDao()

        if (amount < MIN_AMOUNT) {
            throw SavingsPlanMinAmountException("Amount must be 5000 minimum")
        }

        val savingsPlan = savingsPlanDao.getClientSavingsPlan(clientId, savingsTypeId)
        if (savingsPlan != null) {
            savingsPlan.amount = amount
            savingsPlanDao.updateSavingsPlan(savingsPlan)
        }

        return savingsPlan ?: throw NoRecordFoundException("Savings plan not found")
    }

    companion object {
        @JvmStatic
        suspend fun perform(payload: JSONObject): SavingsPlan? {
            val service = UpdateClientSavingsPlanService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}