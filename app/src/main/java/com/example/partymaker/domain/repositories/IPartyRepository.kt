package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import kotlinx.coroutines.flow.Flow

interface IPartyRepository {
    fun listenPartyListFlow(): Flow<DataState<List<Party>>>
    suspend fun insertParty(party: Party): DataState<String>
    suspend fun updateParty(party: Party): DataState<String>
    suspend fun getParty(id: Long): DataState<Party>
    fun getPartyList(): Flow<DataState<List<Party>>>
}
