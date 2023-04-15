package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "civil_statuses")
data class CivilStatus(
    @ColumnInfo(name = "civil_status_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @NonNull
    val name: String,
)
