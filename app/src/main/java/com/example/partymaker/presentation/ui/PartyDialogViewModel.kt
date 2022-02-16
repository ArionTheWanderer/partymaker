package com.example.partymaker.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.usecases.ICreatePartyUseCase
import com.example.partymaker.domain.usecases.IUpdatePartyNameUseCase
import com.example.partymaker.presentation.di.party.PartyScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDialogViewModel
@Inject constructor(
    private val createPartyUseCase: ICreatePartyUseCase,
    private val updatePartyNameUseCase: IUpdatePartyNameUseCase
) : ViewModel() {
    private val _response = MutableStateFlow<DataState<String>>(DataState.Init)

    val response: StateFlow<DataState<String>>
        get() = _response

    fun addData(id: Long, partyName: String) = viewModelScope.launch {
        val party = Party(id, partyName)

        val result = if (id > 0) {
            updatePartyNameUseCase(party)
        } else {
            createPartyUseCase(party)
        }

        _response.value = result

    }
}
