package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LoanWithCreditType(
    @Embedded
    val loan: Loan,

    @Relation(
        parentColumn = "loan_credit_type_id",
        entityColumn = "id"
    )
    val creditType: CreditType
)
