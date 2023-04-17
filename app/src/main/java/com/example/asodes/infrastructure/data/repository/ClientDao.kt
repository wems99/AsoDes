package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.Client
import com.example.asodes.infrastructure.data.local.entity.LoanWithClient

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients")
    fun getAllClients(): List<Client>

    @Query("SELECT * FROM clients WHERE id = :clientId")
    fun getClientById(clientId: Long): Client?

    @Query("SELECT * FROM clients WHERE client_user_id = :userId")
    fun getClientByUserId(userId: Long): Client?

    @Insert
    fun insertClient(client: Client): Long

    @Update
    fun updateClient(client: Client)

    @Delete
    fun deleteClient(client: Client)

}