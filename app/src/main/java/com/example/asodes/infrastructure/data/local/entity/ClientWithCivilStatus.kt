package com.example.asodes.infrastructure.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithCivilStatus(
    @Embedded
    val client: Client,

    @Relation(
        parentColumn = "client_civil_status_id",
        entityColumn = "id"
    )
    val civilStatus: CivilStatus
)
