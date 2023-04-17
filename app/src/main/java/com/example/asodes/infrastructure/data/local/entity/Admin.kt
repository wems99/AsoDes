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


@Entity(tableName = "admins",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["admin_user_id"]
        )
    ],
    indices = [
        Index(value = ["admin_user_id"], name = "index_admins_user_id")
    ])
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "admin_user_id", index = true)
    val userId: Long,
) {
    companion object {
        fun createAdmin(userId: Long): Admin {
            return Admin(0, userId)
        }
    }
}
