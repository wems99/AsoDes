package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.CivilStatus

@Dao
interface CivilStatusDao {
    @Query("SELECT * FROM civil_statuses")
    fun getAllCivilStatuses(): List<CivilStatus>

    @Query("SELECT * FROM civil_statuses WHERE id = :civilStatusId")
    fun getCivilStatusById(civilStatusId: Long): CivilStatus?

    @Insert
    fun insertCivilStatus(civilStatus: CivilStatus): Long

    @Update
    fun updateCivilStatus(civilStatus: CivilStatus)

    @Delete
    fun deleteCivilStatus(civilStatus: CivilStatus)
}