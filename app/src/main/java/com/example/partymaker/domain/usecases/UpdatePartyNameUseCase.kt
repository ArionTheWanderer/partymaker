package com.example.partymaker.domain.usecases

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.presentation.di.main.parties.PartyScope
import javax.inject.Inject

@PartyScope
class UpdatePartyNameUseCase
@Inject constructor(private val repository: IPartyRepository): IUpdatePartyNameUseCase {
    override suspend fun invoke(party: Party): DataState<String> =
        repository.updateParty(party)
}

interface IUpdatePartyNameUseCase {
    suspend operator fun invoke(party: Party): DataState<String>
}
