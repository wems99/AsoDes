package com.example.asodes.infrastructure.services

interface BaseService<T : Any, R: Any> {
    suspend fun execute(payload: T): R?
}