package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "credit_types")
data class CreditType(
    @ColumnInfo(name = "credit_type_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @NonNull
    val name: String,

    @NonNull
    val percentage: Float,
)
