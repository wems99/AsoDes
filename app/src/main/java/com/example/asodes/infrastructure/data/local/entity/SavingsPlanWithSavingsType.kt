package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SavingsPlanWithSavingsType(
    @Embedded
    val savingsPlan: SavingsPlan,

    @Relation(
        parentColumn = "savings_plan_savings_type_id",
        entityColumn = "id"
    )
    val savingsType: SavingsType
)
