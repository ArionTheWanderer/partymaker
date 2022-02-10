package com.example.partymaker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.partymaker.data.db.entities.PartyEntity

@Dao
interface PartyDao {
    @Query("SELECT * FROM party")
    fun getAll(): Flow<List<PartyEntity>>

    @Query("SELECT * FROM party WHERE id = :id")
    suspend fun get(id: Long): PartyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partyEntity: PartyEntity): Long

    @Delete
    suspend fun delete(partyEntity: PartyEntity)

    @Update
    suspend fun update(partyEntity: PartyEntity)
}