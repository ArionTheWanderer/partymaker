package com.example.partymaker.presentation.ui.parties.dialogs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.interactors.IPartyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDeleteDialogViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
): ViewModel() {

    private val _response = MutableStateFlow<DataState<String>>(DataState.Init)

    val response: StateFlow<DataState<String>>
        get() = _response

    fun deleteParty(id: Long) = viewModelScope.launch {
        _response.value = DataState.Loading
        partyInteractor.deleteParty(id)
        _response.value = DataState.Data("Done")
    }

    fun resetErrorMessage() {
        _response.value = DataState.Init
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }
}

private const val TAG = "PartyDeleteDialogViewMo"