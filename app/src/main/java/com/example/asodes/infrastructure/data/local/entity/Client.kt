package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.json.JSONObject
import java.text.SimpleDateFormat
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
    var salary: Double,

    @NonNull
    var phone: String,

    @NonNull
    var address: String,

    @ColumnInfo(name = "date_of_birth")
    @NonNull
    var dateOfBirth: Date,

    @ColumnInfo(name = "client_civil_status_id", index = true)
    @NonNull
    var civilStatusId: Long,
) {
    companion object {
        fun fromJson(payload: JSONObject): Client {
            val id = payload.getLong("id")
            val salary = payload.getDouble("salary")
            val phone = payload.getString("phone")
            val address = payload.getString("address")
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val dateOfBirth = dateFormat.parse(payload.getString("dateOfBirth"))
            val civilStatusId = payload.getLong("civilStatusId")

            return Client(id, id, salary, phone, address, dateOfBirth, civilStatusId)
        }
    }
}