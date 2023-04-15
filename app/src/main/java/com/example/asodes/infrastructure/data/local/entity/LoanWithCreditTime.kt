package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LoanWithCreditTime(
    @Embedded
    val loan: Loan,

    @Relation(
        parentColumn = "loan_credit_time_id",
        entityColumn = "id"
    )
    val creditTime: CreditTime
)
