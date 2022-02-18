package com.example.partymaker.domain.interactors

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartyInteractor
@Inject constructor(
    private val partyRepository: IPartyRepository
): IPartyInteractor {
    override suspend fun createParty(party: Party): DataState<String> =
        partyRepository.insertParty(party)

    override suspend fun updateParty(party: Party): DataState<String> =
        partyRepository.updateParty(party)

    override suspend fun deleteParty(id: Long) =
        partyRepository.deleteParty(id)

    override fun getParty(id: Long): Flow<DataState<Party>> =
        partyRepository.getParty(id)

    override fun getPartyList(): Flow<DataState<List<Party>>> =
        partyRepository.getPartyList()


}

interface IPartyInteractor {
    suspend fun createParty(party: Party): DataState<String>
    suspend fun updateParty(party: Party): DataState<String>
    suspend fun deleteParty(id: Long)
    fun getParty(id: Long): Flow<DataState<Party>>
    fun getPartyList(): Flow<DataState<List<Party>>>
}
