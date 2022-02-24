package com.example.partymaker.data.db

import androidx.room.*
import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.data.db.relations.PartyWithCocktails
import com.example.partymaker.data.db.relations.PartyWithMeals
import kotlinx.coroutines.flow.Flow

@Dao
interface PartyDao {
    @Query("SELECT * FROM party ORDER BY party_id DESC")
    fun getAll(): Flow<List<PartyEntity>>

    @Query("SELECT * FROM party WHERE party_id = :partyId")
    fun get(partyId: Long): Flow<PartyEntity?>

    @Transaction
    @Query("SELECT * FROM party WHERE party_id=:partyId")
    fun getPartyWithMeals(partyId: Long): Flow<List<PartyWithMeals>>

    @Transaction
    @Query("SELECT * FROM party WHERE party_id=:partyId")
    fun getPartyWithCocktails(partyId: Long): Flow<List<PartyWithCocktails>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(partyEntity: PartyEntity): Long

    @Query("DELETE FROM party WHERE party_id = :partyId")
    suspend fun delete(partyId: Long)

    @Query("DELETE FROM party_meal WHERE party_id = :partyId")
    suspend fun deletePartyMealCrossRef(partyId: Long)

    @Query("DELETE FROM party_cocktail WHERE party_id = :partyId")
    suspend fun deletePartyCocktailCrossRef(partyId: Long)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(partyEntity: PartyEntity): Int
}
