package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.services.RetrieveClientSavingsPlans
import com.example.asodes.infrastructure.services.UpdateClientSavingsPlanService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class SavingsPlanController {
    companion object {
        @JvmStatic
        suspend fun retrieveClientPlans(clientId: Long): List<SavingsPlan> {
            val result = GlobalScope.async {
                RetrieveClientSavingsPlans.perform(clientId)
            }

            return result.await()
        }

        @JvmStatic
        suspend fun updateClientPlan(payload: JSONObject): SavingsPlan? {
           val result = GlobalScope.async {
               UpdateClientSavingsPlanService.perform(payload)
           }

            return result.await()
        }
    }
}