package com.example.asodes.infrastructure.seeders

import com.example.asodes.infrastructure.services.CreateCivilStatusService

class SeedCivilStatus {
    companion object {
        @JvmStatic
        fun seed() {
            CreateCivilStatusService.perform("SOLTERO")
            CreateCivilStatusService.perform("CASADO")
        }
    }
}