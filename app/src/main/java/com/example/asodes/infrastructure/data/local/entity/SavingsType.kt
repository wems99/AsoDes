package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings_types")
data class SavingsType(
    @ColumnInfo(name = "savings_type_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @NonNull
    val name: String,
)
