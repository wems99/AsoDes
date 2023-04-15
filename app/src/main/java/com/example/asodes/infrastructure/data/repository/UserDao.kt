package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByUsernamePassword(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Insert()
    suspend fun insertUser(user: User)

    @Update()
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}