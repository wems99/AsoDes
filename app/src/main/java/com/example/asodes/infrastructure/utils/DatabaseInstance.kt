package com.example.asodes.infrastructure.utils

import com.example.asodes.AsoDesUnidos
import com.example.asodes.infrastructure.data.local.database.SQLiteConnection
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private class DatabaseInstance {
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