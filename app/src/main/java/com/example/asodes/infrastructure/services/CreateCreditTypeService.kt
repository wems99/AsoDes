package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateCreditTypeService(private val db: SQLiteConnection) : BaseService<JSONObject, CreditType> {
    override suspend fun execute(payload: JSONObject): CreditType? {
        val creditTypeDao = db.creditTypeDao()
        val name = payload.getString("name")
        val percentage = payload.getDouble("percentage")
        val creditType = CreditType(
            name = name,
            percentage = percentage
        )

       val creditTypeId: Long = creditTypeDao.insertCreditType(creditType)

        return creditTypeDao.getCreditTypeById(creditTypeId)
            ?: throw UnableToCreateRecord("Error while creating a credit type")
    }

    companion object {
        @JvmStatic
        suspend fun perform(creditType: JSONObject): CreditType? {
            val service = CreateCreditTypeService(getDatabaseInstance())
            return service.execute(creditType)
        }
    }
}