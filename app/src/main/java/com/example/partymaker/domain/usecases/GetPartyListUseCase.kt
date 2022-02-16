package com.example.partymaker.domain.usecases

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.presentation.di.party.PartyScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@PartyScope
class GetPartyListUseCase
@Inject constructor(
    private val partyRepository: IPartyRepository
): IGetPartyListUseCase {
    override fun invoke(): Flow<DataState<List<Party>>> =
        partyRepository.getPartyList()
}

interface IGetPartyListUseCase {
    operator fun invoke(): Flow<DataState<List<Party>>>
}
