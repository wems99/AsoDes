package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LoanWithClient(
    @Embedded
    val loan: Loan,

    @Relation(
        parentColumn = "client_id",
        entityColumn = "loan_id"
    )
    val client: Client
)
