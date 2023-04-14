package com.example.asodes.infrastructure.utils

import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection

class DatabaseInstance {
    companion object {
        @JvmStatic
        fun getInstance(): SQLiteConnection {
            return SQLiteConnection.getInstance(AsoDesUnidos.appContext)
        }
    }
}

fun getDatabaseInstance(): SQLiteConnection {
    return DatabaseInstance.getInstance()
}