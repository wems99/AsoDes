package com.example.asodes.infrastructure.services

interface BaseService<T : Any, R: Any> {
    fun execute(payload: T): R?
}