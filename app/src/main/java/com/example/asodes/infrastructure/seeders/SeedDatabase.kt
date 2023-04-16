package com.example.asodes.infrastructure.seeders

import com.example.asodes.infrastructure.utils.BackgroundRunner

class SeedDatabase {
    companion object {
        @JvmStatic
        fun seed() {
            BackgroundRunner.run {
                SeedCivilStatus.seed()
            }
        }
    }
}

fun seedDatabase() {
    SeedDatabase.seed()
}