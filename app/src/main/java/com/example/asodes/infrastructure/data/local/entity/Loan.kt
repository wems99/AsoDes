package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "loans",
    foreignKeys = [
        ForeignKey(
            entity = CreditType::class,
            parentColumns = ["id"],
            childColumns = ["loan_credit_type_id"]
        ),
        ForeignKey(
            entity = CreditTime::class,
            parentColumns = ["id"],
            childColumns = ["loan_credit_time_id"]
        ),
        ForeignKey(
            entity = Client::class,
            parentColumns = ["id"],
            childColumns = ["loan_client_id"],
            onDelete = ForeignKey.CASCADE,
        )
],
    indices = [
        Index(value = ["loan_credit_type_id"], name = "index_loans_credit_type_id"),
        Index(value = ["loan_credit_time_id"], name = "index_loans_credit_time_id"),
        Index(value = ["loan_client_id"], name = "index_loans_client_id"),
    ])
data class Loan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "loan_credit_type_id", index = true)
    @NonNull
    val creditTypeId: Long,

    @ColumnInfo(name = "loan_credit_time_id", index = true)
    @NonNull
    val creditTimeId: Long,

    @ColumnInfo(name = "loan_client_id", index = true)
    @NonNull
    val clientId: Long,
)
