package com.example.asodes.infrastructure.controllers

import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.services.AddLoanContributionService
import com.example.asodes.infrastructure.services.AssignLoanService
import com.example.asodes.infrastructure.services.RetrieveClientLoansService
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
        suspend fun addContribution(loanId: Double, amount: Double) {
            val payload = JSONObject()
            payload.put("loanId", loanId)
            payload.put("amount", amount)

            AddLoanContributionService.perform(payload)
        }
     }
}