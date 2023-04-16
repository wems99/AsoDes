package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AdminWithUser(
    @Embedded
    val admin: Admin,

    @Relation(
        parentColumn = "admin_user_id",
        entityColumn = "id"
    )
    val user: User
)
