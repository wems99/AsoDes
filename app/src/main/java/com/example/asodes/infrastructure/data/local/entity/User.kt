package com.example.asodes.infrastructure.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject
import java.text.SimpleDateFormat

@Entity(tableName = "users")
open class User (
    @PrimaryKey
    val id: Long = 0,

    @NonNull
    var name: String,

    @NonNull
    val username: String,

    @NonNull
    var password: String,

    @ColumnInfo(name = "is_admin")
    @NonNull
    val isAdmin: Boolean,
    ) {
    companion object {
        fun fromJson(payload: JSONObject): User {
            val name = payload.getString("name")
            val username = payload.getString("username")
            val password = payload.getString("password")
            val isAdmin = payload.getBoolean("isAdmin")
            val id = payload.getLong("id")

            return User(id, name, username, password, isAdmin)
        }
    }
}