package com.example.asodes.infrastructure.seeders

import android.util.Log
import com.example.asodes.infrastructure.services.CreateAdminService
import com.example.asodes.infrastructure.services.CreateCreditTypeService
import org.json.JSONObject

class SeedCreateAdmin : BaseSeeder {
    override suspend fun seed() {
        val payload = JSONObject()
        payload.put("name", "Admin Name")
        payload.put("username", "admin")
        payload.put("password", "admin")
        CreateAdminService.perform(payload)
    }


    companion object {
        @JvmStatic
        suspend fun perform() {
            val seeder = SeedCreateAdmin()
            seeder.seed()
        }
    }
}