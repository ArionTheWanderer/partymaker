package com.example.partymaker.domain.usecases

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.presentation.di.main.parties.PartyScope
import javax.inject.Inject

@PartyScope
class GetPartyUseCase
@Inject constructor(private val partyRepository: IPartyRepository): IGetPartyUseCase {
    override suspend fun invoke(id: Long): DataState<Party> =
        partyRepository.getParty(id)
}

interface IGetPartyUseCase {
    suspend operator fun invoke(id: Long): DataState<Party>
}
