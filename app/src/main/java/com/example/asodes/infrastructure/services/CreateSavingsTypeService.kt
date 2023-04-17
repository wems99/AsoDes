package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.data.local.entity.SavingsType
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateSavingsTypeService(private val db: SQLiteConnection) : BaseService<String, SavingsType> {
    override suspend fun execute(payload: String): SavingsType? {
        val savingsTypeDao = db.savingsTypeDao()
        val savingsType = SavingsType(
            name = payload,
        )

       val savingsTypeId: Long = savingsTypeDao.insertSavingsType(savingsType)

        return savingsTypeDao.getSavingsTypeById(savingsTypeId)
            ?: throw UnableToCreateRecord("Error while creating a savings type")
    }

    companion object {
        @JvmStatic
        suspend fun perform(savingsTypeName: String): SavingsType? {
            val service = CreateSavingsTypeService(getDatabaseInstance())
            return service.execute(savingsTypeName)
        }
    }
}