package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateCreditTimeService(private val db: SQLiteConnection) : BaseService<JSONObject, CreditTime> {
    override suspend fun execute(payload: JSONObject): CreditTime? {
        val creditTimeDao = db.creditTimeDao()
        val name = payload.getString("name")
        val years = payload.getInt("years")
        val creditTime = CreditTime(
            name = name,
            years = years
        )

       val creditTimeId = creditTimeDao.insertCreditTime(creditTime)

        return creditTimeDao.getCreditTimeById(creditTimeId)
            ?: throw UnableToCreateRecord("Error while creating a credit time")
    }

    companion object {
        @JvmStatic
        suspend fun perform(creditTime: JSONObject): CreditTime? {
            val service = CreateCreditTimeService(getDatabaseInstance())
            return service.execute(creditTime)
        }
    }
}