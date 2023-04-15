package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "savings_plans",
    primaryKeys = ["client_id", "savings_type_id"],
    foreignKeys = [
        ForeignKey(entity = SavingsType::class, parentColumns = ["savings_type_id"], childColumns = ["savings_type_id"]),
        ForeignKey(entity = Client::class, parentColumns = ["client_id"], childColumns = ["client_id"]),
    ]
)
data class SavingsPlan(
    @ColumnInfo(name = "client_id")
    val clientId: Long,

    @Embedded
    val client: Client,

    @ColumnInfo(name = "savings_type_id")
    @NonNull
    val savingsTypeId: Long,

    @Embedded
    val savingsType: SavingsType,

    @NonNull
    val amount: Double
)
