package com.example.asodes.infrastructure.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asodes.infrastructure.data.local.entity.Admin
import com.example.asodes.infrastructure.data.local.entity.CivilStatus
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.CreditTime
import com.example.asodes.infrastructure.data.local.entity.CreditType
import com.example.asodes.infrastructure.data.local.entity.Loan
import com.example.asodes.infrastructure.data.local.entity.LoanWithClient
import com.example.asodes.infrastructure.data.local.entity.SavingsPlan
import com.example.asodes.infrastructure.data.local.entity.SavingsType
import com.example.asodes.infrastructure.data.local.entity.User
import com.example.asodes.infrastructure.data.repository.AdminDao
import com.example.asodes.infrastructure.data.repository.CivilStatusDao
import com.example.asodes.infrastructure.data.repository.ClientDao
import com.example.asodes.infrastructure.data.repository.CreditTimeDao
import com.example.asodes.infrastructure.data.repository.CreditTypeDao
import com.example.asodes.infrastructure.data.repository.LoanDao
import com.example.asodes.infrastructure.data.repository.SavingsPlanDao
import com.example.asodes.infrastructure.data.repository.SavingsTypeDao
import com.example.asodes.infrastructure.data.repository.UserDao

@Database(entities = [
    Admin::class,
    CivilStatus::class,
    Client::class,
    CreditTime::class,
    CreditType::class,
    Loan::class,
    LoanWithClient::class,
    SavingsType::class,
    SavingsPlan::class,
    User::class],
    version = 1)
abstract class SQLiteConnection : RoomDatabase() {
    abstract fun adminDao(): AdminDao
    abstract fun civilStatusDao(): CivilStatusDao
    abstract fun clientDao(): ClientDao
    abstract fun creditTimeDao(): CreditTimeDao
    abstract fun creditTypeDao(): CreditTypeDao
    abstract fun loanDao(): LoanDao
    abstract fun savingsPlanDao(): SavingsPlanDao
    abstract fun savingsTypeDao(): SavingsTypeDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: SQLiteConnection? = null

        /**
        * This function returns an instance of the SQLiteConnection object.
        * It uses the Singleton pattern to ensure that only one instance of the database is created.
        * @param context The context of the calling activity or application
        * @return An instance of the SQLiteConnection object
         */
        fun getInstance(context: Context): SQLiteConnection {
            return instance
                ?: synchronized(this) {
                    instance
                    ?: Room.databaseBuilder(
                    context.applicationContext,
                    SQLiteConnection::class.java,
                    "asodesunidos.db"
                ).build().also { instance = it }
            }
        }
    }
}