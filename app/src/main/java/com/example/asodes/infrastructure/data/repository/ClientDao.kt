package com.example.asodes.infrastructure.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.asodes.infrastructure.data.local.entity.Client

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients")
    suspend fun getAllClients(): List<Client>

    @Query("SELECT * FROM clients WHERE client_id = :clientId")
    suspend fun getClientById(clientId: Long): Client?

    @Query("SELECT * FROM clients WHERE user_id = :userId")
    suspend fun getClientByUserId(userId: Long): Client?

    @Insert()
    suspend fun insertClient(client: Client)

    @Update()
    suspend fun updateClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

}