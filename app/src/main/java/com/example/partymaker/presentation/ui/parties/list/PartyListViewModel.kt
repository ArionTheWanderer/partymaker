package com.example.partymaker.presentation.ui.parties.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.PartyDomain
import com.example.partymaker.domain.interactors.IPartyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyListViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
): ViewModel() {
    private val _partyList = MutableStateFlow<DataState<List<PartyDomain>>>(DataState.Init)

    val partyList: StateFlow<DataState<List<PartyDomain>>>
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
        Log.d(TAG, "onCleared")
        super.onCleared()
    }
}

private const val TAG = "PartyListViewModel"
