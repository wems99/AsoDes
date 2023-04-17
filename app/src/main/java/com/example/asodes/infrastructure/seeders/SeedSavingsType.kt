package com.example.asodes.infrastructure.seeders

import android.util.Log
import com.example.asodes.infrastructure.services.CreateSavingsTypeService

class SeedSavingsType: BaseSeeder {
    override suspend fun seed() {
        CreateSavingsTypeService.perform("CHRISTMAS")
        CreateSavingsTypeService.perform("SCHOOL")
        CreateSavingsTypeService.perform("LABEL") // MARCHAMO
        CreateSavingsTypeService.perform("EXTRAORDINARY")
    }

    companion object {
        @JvmStatic
        suspend fun perform() {
            val seeder = SeedSavingsType()
            seeder.seed()
        }
    }
}