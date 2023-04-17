package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.services.RetrieveLoanCreditTypes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CreditTypeController {
    companion object {
        @JvmStatic
        suspend fun retrieveAll(): List<CreditType> {
            val result = GlobalScope.async {
                RetrieveLoanCreditTypes.perform()
            }

            return result.await()
        }
    }
}