package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "loans",
    foreignKeys = [
        ForeignKey(
            entity = CreditType::class,
            parentColumns = ["loan_id"],
            childColumns = ["credit_type_id"]
        ),
        ForeignKey(
            entity = CreditTime::class,
            parentColumns = ["loan_id"],
            childColumns = ["credit_time_id"]
        ),
        ForeignKey(
            entity = Client::class,
            parentColumns = ["loan_id"],
            childColumns = ["client_id"],
            onDelete = ForeignKey.CASCADE,
        )
])
data class Loan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "loan_id")
    val id: Long,

    @ColumnInfo(name = "credit_type_id", index = true)
    val creditTypeId: Long,

    @Embedded
    val creditType: CreditType,

    @ColumnInfo(name = "credit_time_id", index = true)
    @NonNull
    val creditTimeId: Long,

    @Embedded
    val creditTime: CreditTime,

    @ColumnInfo(name = "client_id", index = true)
    @NonNull
    val clientId: Long,

    @Embedded
    val client: Client,
)
