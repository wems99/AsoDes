package com.example.asodes.infrastructure.seeders

import android.util.Log
import com.example.asodes.infrastructure.services.CreateCreditTimeService
import org.json.JSONObject

class SeedCreditTime: BaseSeeder {
    override suspend fun seed() {
        CreateCreditTimeService.perform(createCreditTimePayload("SHORT", 3))
        CreateCreditTimeService.perform(createCreditTimePayload("MEDIUM", 5))
        CreateCreditTimeService.perform(createCreditTimePayload("LONG", 10))
    }

    private fun createCreditTimePayload(name: String, years: Int): JSONObject {
        val creditTime = JSONObject()
        creditTime.put("name", name);
        creditTime.put("years", years);
        return creditTime
    }

    companion object {
        @JvmStatic
        suspend fun perform() {
            val seeder = SeedCreditTime()
            seeder.seed()
        }
    }
}