package com.example.partymaker.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.interactors.IPartyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDialogViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
) : ViewModel() {
    private val _response = MutableStateFlow<DataState<String>>(DataState.Init)

    val response: StateFlow<DataState<String>>
        get() = _response

    fun addData(id: Long, partyName: String) = viewModelScope.launch {
        val party = Party(id, partyName)

        val result = if (id > 0) {
            partyInteractor.updateParty(party)
        } else {
            partyInteractor.createParty(party)
        }

        _response.value = result

    }
}
