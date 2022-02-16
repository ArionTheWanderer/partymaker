package com.example.partymaker.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.domain.usecases.IGetPartyListUseCase
import com.example.partymaker.presentation.di.Injector
import com.example.partymaker.presentation.di.party.PartyScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyListViewModel
@Inject constructor(
    private val getPartyListUseCase: IGetPartyListUseCase
): ViewModel() {
    private val _partyList = MutableStateFlow<DataState<List<Party>>>(DataState.Init)

    val partyList: StateFlow<DataState<List<Party>>>
        get() = _partyList

    fun getPartyList() = viewModelScope.launch {
        _partyList.value = DataState.Loading
        getPartyListUseCase().collect {
            _partyList.value = it
        }
    }

    override fun onCleared() {
        Injector.clearPartyComponent()
        super.onCleared()
    }
}
