package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "clients",
    foreignKeys = [
       ForeignKey(
           entity = User::class,
           parentColumns = ["id"],
           childColumns = ["client_user_id"]
       )
   ],
    indices = [
        Index(value = ["client_user_id"], name = "index_clients_user_id"),
        Index(value = ["client_civil_status_id"], name = "index_clients_civil_status_id")
    ])
data class Client (
    @PrimaryKey
    val id: Long = 0,

    @ColumnInfo(name = "client_user_id", index = true)
    val userId: Long,

    @NonNull
    val salary: Double,

    @NonNull
    val phone: String,

    @ColumnInfo(name = "date_of_birth")
    @NonNull
    val dateOfBirth: Date,

    @ColumnInfo(name = "client_civil_status_id", index = true)
    @NonNull
    val civilStatusId: Long,
)