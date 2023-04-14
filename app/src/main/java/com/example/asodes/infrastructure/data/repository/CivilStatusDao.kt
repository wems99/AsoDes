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
    suspend fun getAllCivilStatuses(): List<CivilStatus>

    @Query("SELECT * FROM civil_statuses WHERE civil_status_id = :civilStatusId")
    suspend fun getCivilStatusById(civilStatusId: Long): CivilStatus?

    @Insert()
    suspend fun insertCivilStatus(civilStatus: CivilStatus)

    @Update()
    suspend fun updateCivilStatus(civilStatus: CivilStatus)

    @Delete
    suspend fun deleteCivilStatus(civilStatus: CivilStatus)
}