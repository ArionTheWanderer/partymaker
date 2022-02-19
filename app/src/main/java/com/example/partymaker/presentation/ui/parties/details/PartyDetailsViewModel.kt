package com.example.partymaker.presentation.ui.parties.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.interactors.IPartyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDetailsViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
): ViewModel() {
    private val _party = MutableStateFlow<DataState<Party>>(DataState.Init)

    val party: StateFlow<DataState<Party>>
        get() = _party

    fun getParty(partyId: Long) = viewModelScope.launch{
        _party.value = DataState.Loading
        partyInteractor.getParty(partyId).collect { party ->
            _party.value = party
        }
    }
}