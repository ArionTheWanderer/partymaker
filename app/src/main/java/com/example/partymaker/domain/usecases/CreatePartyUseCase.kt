package com.example.partymaker.domain.usecases

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party

class CreatePartyUseCase: ICreatePartyUseCase {
    override suspend fun invoke(party: Party): DataState<String> {
        TODO("Not yet implemented")
    }
}