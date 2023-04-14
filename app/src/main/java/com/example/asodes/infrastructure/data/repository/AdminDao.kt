package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.Admin

@Dao
interface AdminDao {
    @Query("SELECT * FROM admins")
    suspend fun getAllAdmins(): List<Admin>

    @Query("SELECT * FROM admins WHERE admin_id = :adminId")
    suspend fun getAdminById(adminId: Long): Admin?

    @Query("SELECT * FROM admins WHERE user_id = :userId")
    suspend fun getAdminByUserId(userId: Long): Admin?

    @Insert()
    suspend fun insertAdmin(admin: Admin)

    @Update()
    suspend fun updateAdmin(admin: Admin)

    @Delete
    suspend fun deleteAdmin(admin: Admin)

}