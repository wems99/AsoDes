package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.data.local.entity.SavingsType
import com.example.asodes.infrastructure.services.RetrieveCivilStatusService
import com.example.asodes.infrastructure.services.RetrieveLoanCreditTimes
import com.example.asodes.infrastructure.services.RetrieveSavingsTypes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class SavingsTypeController {
    companion object {
        @JvmStatic
        suspend fun retrieveAll(): List<SavingsType> {
            val result = GlobalScope.async {
                RetrieveSavingsTypes.perform()
            }

            return result.await()
        }
    }
}