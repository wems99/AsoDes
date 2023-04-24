package com.example.asodes.infrastructure.controllers

import android.provider.Settings.Global
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.services.AddLoanContributionService
import com.example.asodes.infrastructure.services.AssignLoanService
import com.example.asodes.infrastructure.services.RetrieveClientLoansService
import com.example.asodes.infrastructure.services.UpdateLoanService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class LoanController {

    companion object {
        @JvmStatic
        suspend fun assignLoan(payload: JSONObject): Loan? {
            val result = GlobalScope.async {
                AssignLoanService.perform(payload)
            }

            return result.await()
        }

        @JvmStatic
        suspend fun retrieveLoansByClient(clientId: Long): List<Loan> {
            val result = GlobalScope.async {
                RetrieveClientLoansService.perform(clientId)
            }

            return result.await()
        }

        @JvmStatic
        suspend fun updateLoan(loan: Loan): Loan {
            val result = GlobalScope.async {
                UpdateLoanService.perform(loan)
            }

            return result.await()
        }
     }
}