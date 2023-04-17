package com.example.asodes.infrastructure.seeders

import android.util.Log
import com.example.asodes.infrastructure.services.CreateCreditTypeService
import org.json.JSONObject

class SeedCreditType : BaseSeeder {
    override suspend fun seed() {
        CreateCreditTypeService.perform(createCreditTypePayload("MORTGAGE", 7.5))
        CreateCreditTypeService.perform(createCreditTypePayload("EDUCATION", 8.0))
        CreateCreditTypeService.perform(createCreditTypePayload("PERSONAL", 10.0))
        CreateCreditTypeService.perform(createCreditTypePayload("TRIPS", 12.0))
    }

    private fun createCreditTypePayload(name: String, percentage: Double): JSONObject {
        val creditType = JSONObject()
        creditType.put("name", name);
        creditType.put("percentage", percentage);
        return creditType
    }

    companion object {
        @JvmStatic
        suspend fun perform() {
            val seeder = SeedCreditType()
            seeder.seed()
        }
    }
}