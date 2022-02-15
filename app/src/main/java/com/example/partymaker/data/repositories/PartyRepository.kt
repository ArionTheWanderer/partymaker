package com.example.partymaker.data.repositories

import com.example.partymaker.data.common.PartyEntityMapper
import com.example.partymaker.data.datasources.IPartyLocalDataSource
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.presentation.di.main.parties.PartyScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
        if (newId > 0)
            DataState.Data("Successfully inserted. Id = $newId")
        else
            DataState.Error("Not inserted.")
    }

    override suspend fun updateParty(party: Party): DataState<String> = withContext(Dispatchers.IO) {
        val rowUpdated = partyLocalDataSource.updateParty(partyEntityMapper.mapFromDomainModel(party))
        if (rowUpdated == 1)
            DataState.Data("Successfully updated")
        else
            DataState.Error("Not updated.")

    }

    override suspend fun getParty(id: Long): DataState<Party> = withContext(Dispatchers.IO) {
        val partyEntity = partyLocalDataSource.getParty(id)
        if (partyEntity != null) {
            val party = partyEntityMapper.mapToDomainModel(partyEntity)
            DataState.Data(party)
        } else DataState.Error("Not found")
    }
}
