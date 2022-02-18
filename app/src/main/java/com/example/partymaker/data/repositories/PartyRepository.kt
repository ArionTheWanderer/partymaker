package com.example.partymaker.data.repositories

import android.util.Log
import com.example.partymaker.data.common.PartyEntityMapper
import com.example.partymaker.data.datasources.IPartyLocalDataSource
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.presentation.di.party.PartyScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@PartyScope
class PartyRepository
@Inject constructor(
    private val partyLocalDataSource: IPartyLocalDataSource,
    private val partyEntityMapper: PartyEntityMapper
) : IPartyRepository {

    override suspend fun insertParty(party: Party): DataState<String> = withContext(Dispatchers.IO) {
        val newId = partyLocalDataSource.insertParty(partyEntityMapper.mapFromDomainModel(party))
        if (newId > -1) {
            Log.d(TAG, "Returned id = $newId")
            DataState.Data("Successfully inserted. Id = $newId")
        }
        else {
            Log.e(TAG, "Error on inserted. Returned id = $newId")
            DataState.Error("Party with the same name already exists.")
        }
    }

    override suspend fun updateParty(party: Party): DataState<String> = withContext(Dispatchers.IO) {
        val rowUpdated = partyLocalDataSource.updateParty(partyEntityMapper.mapFromDomainModel(party))
        if (rowUpdated == 1)
            DataState.Data("Successfully updated")
        else
            DataState.Error("Party with the same name already exists.")
    }

    override suspend fun deleteParty(id: Long) = withContext(Dispatchers.IO) {
        partyLocalDataSource.deleteParty(id)
    }

    override fun getParty(id: Long): Flow<DataState<Party>> {
        val partyState = partyLocalDataSource.getParty(id).transform { partyEntity ->
            if (partyEntity != null) {
                val party = partyEntityMapper.mapToDomainModel(partyEntity)
                emit(DataState.Data(party))
            } else
                emit(DataState.Error("Not found"))
        }
        return partyState
    }

    override fun getPartyList(): Flow<DataState<List<Party>>> {
        val partyEntityList = partyLocalDataSource.getAllParties().transform { partyEntityList ->
            val partyList = partyEntityList.map {
                partyEntityMapper.mapToDomainModel(it)
            }
            emit(DataState.Data(partyList))
        }
        return partyEntityList
        /*val partyList = partyEntityList.map {
            partyEntityMapper.mapToDomainModel(it)
        }
        _partyListFlow.emit(DataState.Data(partyList))
        DataState.Data(partyList)*/
    }
}

private const val TAG = "PartyRepository"
