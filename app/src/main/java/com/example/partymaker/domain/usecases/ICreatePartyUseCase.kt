package com.example.partymaker.domain.usecases

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party

interface ICreatePartyUseCase {
    suspend operator fun invoke(party: Party): DataState<String>
}