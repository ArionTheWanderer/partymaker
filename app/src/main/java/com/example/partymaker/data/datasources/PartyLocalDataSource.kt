package com.example.partymaker.data.datasources

import android.util.Log
import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.presentation.di.party.PartyScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IPartyLocalDataSource {
    suspend fun updateParty(partyEntity: PartyEntity): Int
    suspend fun insertParty(partyEntity: PartyEntity): Long
    fun getParty(id: Long): Flow<PartyEntity?>
    fun getAllParties(): Flow<List<PartyEntity>>
}

private const val TAG = "PartyLocalDataSource"

@PartyScope
class PartyLocalDataSource
@Inject constructor(private val partyDao: PartyDao) : IPartyLocalDataSource {

    override suspend fun updateParty(partyEntity: PartyEntity): Int {
        return partyDao.update(partyEntity)
        /*val newId = partyDao.update(partyEntity)
        return if (newId == 1)
            DataState.Data("Successfully updated")
        else
            DataState.Error("Not updated.")*/
    }

    override suspend fun insertParty(partyEntity: PartyEntity): Long {
        Log.d(TAG, "BEFORE INSERT: $partyEntity")
        return partyDao.insert(partyEntity)
        /*val newId = partyDao.insert(partyEntity)
        return if (newId > 0)
            DataState.Data("Successfully inserted. Id = $newId")
        else
            DataState.Error("Not inserted.")*/
    }

    override fun getParty(id: Long): Flow<PartyEntity?> =
        partyDao.get(id)

    override fun getAllParties(): Flow<List<PartyEntity>> =
        partyDao.getAll()

}
