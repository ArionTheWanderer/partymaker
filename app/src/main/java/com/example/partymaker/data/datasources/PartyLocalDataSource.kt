package com.example.partymaker.data.datasources

import android.util.Log
import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.entities.PartyEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface IPartyLocalDataSource {
    suspend fun updateParty(partyEntity: PartyEntity): Int
    suspend fun insertParty(partyEntity: PartyEntity): Long
    suspend fun deleteParty(id: Long)
    fun getParty(id: Long): Flow<PartyEntity?>
    fun getAllParties(): Flow<List<PartyEntity>>
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

    override suspend fun deleteParty(id: Long) =
        partyDao.delete(id)

    override fun getParty(id: Long): Flow<PartyEntity?> =
        partyDao.get(id)

    override fun getAllParties(): Flow<List<PartyEntity>> =
        partyDao.getAll()

}
