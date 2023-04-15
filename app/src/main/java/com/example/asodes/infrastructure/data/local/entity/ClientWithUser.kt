package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithUser(
    @Embedded
    val client: Client,

    @Relation(
        parentColumn = "client_user_id",
        entityColumn = "id"
    )
    val user: User
)
