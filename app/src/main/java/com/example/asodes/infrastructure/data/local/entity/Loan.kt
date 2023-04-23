package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.json.JSONObject

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

    @NonNull
    @IntRange(from = 1, to = 45)
    val percentage: Int,

    @NonNull
    val clientSalary: Double,

    @NonNull
    var contributions: Double
) {
    companion object {
        @JvmStatic
        fun fromJson(payload: JSONObject): Loan {
            val creditTypeId = payload.getLong("creditTypeId")
            val creditTimeId = payload.getLong("creditTimeId")
            val clientId = payload.getLong("clientId")
            val percentage = payload.getInt("percentage")
            val clientSalary = payload.getDouble("clientSalary")

            return Loan(0, creditTypeId, creditTimeId, clientId, percentage, clientSalary, 0.0)
        }
    }

    fun calculateLoanAmount(): Double {
        return clientSalary * (percentage / 100)
    }
    
    fun calculateLeftLoanAmount(): Double {
        return calculateLoanAmount() - contributions
    }

    fun addContribution(contribution: Double) {
        contributions += contribution
    }

}
