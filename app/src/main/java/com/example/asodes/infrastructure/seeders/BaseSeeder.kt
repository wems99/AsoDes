package com.example.asodes.infrastructure.seeders

interface BaseSeeder {
    suspend fun seed(): Unit
}