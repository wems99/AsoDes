package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "civil_statuses")
data class CivilStatus(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @NonNull
    val name: String,
)
