package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.services.RetrieveCreditTimeById
import com.example.asodes.infrastructure.services.RetrieveLoanCreditTimes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CreditTimeController {
    companion object {
        @JvmStatic
        suspend fun retrieveAll(): List<CreditTime> {
            val result = GlobalScope.async {
                RetrieveLoanCreditTimes.perform()
            }

            return result.await()
        }

        @JvmStatic
        suspend fun retrieveCreditTime(payload: Long): CreditTime? {
            val result = GlobalScope.async {
                RetrieveCreditTimeById.perform(payload)
            }

            return result.await()
        }
    }
}