package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "credit_times")
data class CreditTime(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @NonNull
    val name: String,

    @NonNull
    val years: Int,
)
