package com.example.partymaker.data.datasources

import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.di.main.parties.PartyScope
import javax.inject.Inject

interface IPartyLocalDataSource {
    suspend fun updateParty(partyEntity: PartyEntity): Int
    suspend fun insertParty(partyEntity: PartyEntity): Long
    suspend fun getParty(id: Long): PartyEntity?
    suspend fun getAllParties()
}

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
        return partyDao.insert(partyEntity)
        /*val newId = partyDao.insert(partyEntity)
        return if (newId > 0)
            DataState.Data("Successfully inserted. Id = $newId")
        else
            DataState.Error("Not inserted.")*/
    }

    override suspend fun getParty(id: Long): PartyEntity? {
        return partyDao.get(id)
        /*val party = partyDao.get(id)
        return if (party != null)
            DataState.Data(party)
        else DataState.Error("Not found")*/
    }

    override suspend fun getAllParties() {
        TODO("Not yet implemented")
    }

}
