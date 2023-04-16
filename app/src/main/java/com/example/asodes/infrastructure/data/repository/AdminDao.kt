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
    fun getAllAdmins(): List<Admin>

    @Query("SELECT * FROM admins WHERE id = :adminId")
    fun getAdminById(adminId: Long): Admin?

    @Query("SELECT * FROM admins WHERE admin_user_id = :userId")
    fun getAdminByUserId(userId: Long): Admin?

    @Insert
    fun insertAdmin(admin: Admin)

    @Update
    fun updateAdmin(admin: Admin)

    @Delete
    fun deleteAdmin(admin: Admin)

}