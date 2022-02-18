package com.example.partymaker.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.interactors.IPartyInteractor
import com.example.partymaker.presentation.di.Injector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyListViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
): ViewModel() {
    private val _partyList = MutableStateFlow<DataState<List<Party>>>(DataState.Init)

    val partyList: StateFlow<DataState<List<Party>>>
        get() = _partyList

    fun getPartyList() = viewModelScope.launch {
        _partyList.value = DataState.Loading
        partyInteractor.getPartyList().collect {
            _partyList.value = it
        }
    }

    fun resetErrorMessage() {
        _partyList.value = DataState.Init
    }

    override fun onCleared() {
        Injector.clearPartyComponent()
        super.onCleared()
    }
}
