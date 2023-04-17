package com.example.asodes.infrastructure.seeders

import android.util.Log
import com.example.asodes.infrastructure.services.CreateCivilStatusService

class SeedCivilStatus : BaseSeeder {
    override suspend fun seed() {
        CreateCivilStatusService.perform("SINGLE")
        CreateCivilStatusService.perform("MARRIED")
        CreateCivilStatusService.perform("DIVORCED")
    }
    companion object {
        @JvmStatic
        suspend fun perform() {
           val seeder = SeedCivilStatus()
           seeder.seed()
        }
    }
}