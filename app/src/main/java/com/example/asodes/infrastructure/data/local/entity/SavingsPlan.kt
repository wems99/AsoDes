package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "savings_plans",
    primaryKeys = ["savings_plan_client_id", "savings_plan_savings_type_id"],
    foreignKeys = [
        ForeignKey(entity = SavingsType::class, parentColumns = ["id"], childColumns = ["savings_plan_savings_type_id"]),
        ForeignKey(entity = Client::class, parentColumns = ["id"], childColumns = ["savings_plan_client_id"]),
    ],
    indices = [
        Index(value = ["savings_plan_savings_type_id"], name = "index_savings_plans_savings_type_id"),
        Index(value = ["savings_plan_client_id"], name = "index_savings_plans_client_id")
    ]
)
data class SavingsPlan(
    @ColumnInfo(name = "savings_plan_client_id", index = true)
    val clientId: Long,

    @ColumnInfo(name = "savings_plan_savings_type_id", index = true)
    @NonNull
    val savingsTypeId: Long,

    @NonNull
    val amount: Double
)
