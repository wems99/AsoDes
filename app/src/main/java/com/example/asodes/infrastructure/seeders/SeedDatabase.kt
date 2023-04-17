package com.example.asodes.infrastructure.seeders

import android.util.Log
import com.example.asodes.infrastructure.utils.BackgroundRunner

class SeedDatabase {
    companion object {
        @JvmStatic
        fun seed() {
            BackgroundRunner.run {
                SeedCivilStatus.perform()
                SeedCreditType.perform()
                SeedSavingsType.perform()
                SeedCreditTime.perform()
                SeedCreateAdmin.perform()
                Log.d("Seeder", "Database seeded")
            }
        }
    }
}

fun seedDatabase() {
    SeedDatabase.seed()
}