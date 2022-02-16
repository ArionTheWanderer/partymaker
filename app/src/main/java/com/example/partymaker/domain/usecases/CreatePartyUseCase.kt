package com.example.partymaker.domain.usecases

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.presentation.di.party.PartyScope
import javax.inject.Inject

@PartyScope
class CreatePartyUseCase
@Inject constructor(
    private val repository: IPartyRepository
): ICreatePartyUseCase {
    override suspend fun invoke(party: Party): DataState<String> =
        repository.insertParty(party)
}

interface ICreatePartyUseCase {
    suspend operator fun invoke(party: Party): DataState<String>
}
