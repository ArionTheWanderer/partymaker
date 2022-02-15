package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party

interface IPartyRepository {
    suspend fun insertParty(party: Party): DataState<String>
    suspend fun updateParty(party: Party): DataState<String>
    suspend fun getParty(id: Long): DataState<Party>
}
