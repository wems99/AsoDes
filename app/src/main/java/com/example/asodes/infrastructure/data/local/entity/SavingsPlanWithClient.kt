package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SavingsPlanWithClient(
    @Embedded
    val savingsPlan: SavingsPlan,

    @Relation(
        parentColumn = "savings_plan_client_id",
        entityColumn = "id"
    )
    val client: Client
)
