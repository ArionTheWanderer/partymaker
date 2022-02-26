package com.example.partymaker.data.datasources

import android.util.Log
import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.data.db.relations.PartyWithCocktails
import com.example.partymaker.data.db.relations.PartyWithMeals
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface IPartyLocalDataSource {
    suspend fun updateParty(partyEntity: PartyEntity): Int
    suspend fun insertParty(partyEntity: PartyEntity): Long
    suspend fun deleteParty(partyId: Long)
    fun getParty(partyId: Long): Flow<PartyEntity?>
    fun getAllParties(): Flow<List<PartyEntity>>
    fun getPartyWithMeals(partyId: Long): Flow<List<PartyWithMeals>>
    fun getPartyWithCocktails(partyId: Long): Flow<List<PartyWithCocktails>>
    suspend fun getPartyWithMealsSus(partyId: Long): PartyWithMeals
    suspend fun getPartyWithCocktailsSus(partyId: Long): PartyWithCocktails
}

private const val TAG = "PartyLocalDataSource"

@Singleton
class PartyLocalDataSource
@Inject constructor(private val partyDao: PartyDao) : IPartyLocalDataSource {

    override suspend fun updateParty(partyEntity: PartyEntity): Int {
        return partyDao.update(partyEntity)
    }

    override suspend fun insertParty(partyEntity: PartyEntity): Long {
        Log.d(TAG, "BEFORE INSERT: $partyEntity")
        return partyDao.insert(partyEntity)
    }

    override suspend fun deleteParty(partyId: Long) {
        partyDao.deletePartyMealCrossRef(partyId)
        partyDao.deletePartyCocktailCrossRef(partyId)
        partyDao.delete(partyId)
    }

    override fun getParty(partyId: Long): Flow<PartyEntity?> =
        partyDao.get(partyId)

    override fun getAllParties(): Flow<List<PartyEntity>> =
        partyDao.getAll()

    override fun getPartyWithMeals(partyId: Long): Flow<List<PartyWithMeals>> =
        partyDao.getPartyWithMeals(partyId)

    override fun getPartyWithCocktails(partyId: Long): Flow<List<PartyWithCocktails>> =
        partyDao.getPartyWithCocktails(partyId)

    override suspend fun getPartyWithMealsSus(partyId: Long): PartyWithMeals =
        partyDao.getPartyWithMealsSus(partyId)

    override suspend fun getPartyWithCocktailsSus(partyId: Long): PartyWithCocktails =
        partyDao.getPartyWithCocktailsSus(partyId)

}
