package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "clients",
    foreignKeys = [
       ForeignKey(
           entity = CivilStatus::class,
           parentColumns = ["client_id"],
           childColumns = ["civil_status_id"]
       )
   ])
data class Client (
    @ColumnInfo(name = "client_id")
    @PrimaryKey
    val id: Long,

    @Embedded
    val user: User,

    @NonNull
    val salary: Double,

    @NonNull
    val phone: String,

    @ColumnInfo(name = "date_of_birth")
    @NonNull
    val dateOfBirth: Date,

    @ColumnInfo(name = "civil_status_id")
    @NonNull
    val civilStatusId: Long,

    @Embedded
    val civilStatus: CivilStatus
)