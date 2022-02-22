package com.example.partymaker.data.db

import androidx.room.*
import com.example.partymaker.data.db.entities.PartyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartyDao {
    @Query("SELECT * FROM party ORDER BY party_id DESC")
    fun getAll(): Flow<List<PartyEntity>>

    @Query("SELECT * FROM party WHERE party_id = :id")
    fun get(id: Long): Flow<PartyEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(partyEntity: PartyEntity): Long

    @Query("DELETE FROM party WHERE party_id = :id")
    suspend fun delete(id: Long)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(partyEntity: PartyEntity): Int
}
