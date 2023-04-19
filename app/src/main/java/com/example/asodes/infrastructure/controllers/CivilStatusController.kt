package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.services.RetrieveCivilStatusService
import com.example.asodes.infrastructure.services.RetrieveLoanCreditTimes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CivilStatusController {
    companion object {
        @JvmStatic
        suspend fun retrieveAll(): List<CivilStatus> {
            val result = GlobalScope.async {
                RetrieveCivilStatusService.perform()
            }

            return result.await()
        }
    }
}