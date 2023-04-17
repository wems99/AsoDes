package com.example.asodes.infrastructure.services

import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.exceptions.UnableToCreateRecord
import com.example.asodes.infrastructure.utils.BackgroundRunner
import com.example.asodes.infrastructure.utils.getDatabaseInstance
import org.json.JSONObject

class CreateClientService(private val db: SQLiteConnection) : BaseService<JSONObject, Client> {
    override suspend fun execute(payload: JSONObject): Client? {
        val userPayload = payload.getJSONObject("user")
        userPayload.put("isAdmin", false)
        val user = CreateUserService.perform(userPayload)
        val clientDao = db.clientDao()

        payload.put("userId", user!!.id)
        val client = Client.fromJson(payload)

        val clientId = clientDao.insertClient(client)

        createSavingsPlans(clientId)

        return clientDao.getClientById(clientId)
            ?: throw UnableToCreateRecord("Error occurred while creating client")
    }

    /**
     * This function creates savings plans for the specified client ID.
     * @param clientId the ID of the client for which to create savings plans
     */
    private suspend fun createSavingsPlans(clientId: Long) {
        BackgroundRunner.run {
            val savingsTypes = RetrieveSavingsTypes.perform()
            savingsTypes.forEach {savingsTypes ->
                val savingsPlanPayload = JSONObject()
                savingsPlanPayload.put("ClientId", clientId)
                savingsPlanPayload.put("savingsPlanId", savingsTypes.id)
                CreateInitialSavingsPlan.perform(savingsPlanPayload)
            }
        }
    }

    companion object {
        @JvmStatic
       suspend fun perform(payload: JSONObject): Client? {
            val service = CreateClientService(getDatabaseInstance())
            return service.execute(payload)
        }
    }
}

