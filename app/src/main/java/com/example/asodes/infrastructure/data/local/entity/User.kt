package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
open class User (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @NonNull
    val name: String,

    @NonNull
    val username: String,

    @NonNull
    val password: String,

    @ColumnInfo(name = "is_admin")
    @NonNull
    val isAdmin: Boolean,
    )