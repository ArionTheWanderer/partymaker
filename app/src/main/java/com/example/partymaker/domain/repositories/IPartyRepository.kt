package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import kotlinx.coroutines.flow.Flow

interface IPartyRepository {
    suspend fun insertParty(party: Party): DataState<String>
    suspend fun updateParty(party: Party): DataState<String>
    fun getParty(id: Long): Flow<DataState<Party>>
    fun getPartyList(): Flow<DataState<List<Party>>>
}
