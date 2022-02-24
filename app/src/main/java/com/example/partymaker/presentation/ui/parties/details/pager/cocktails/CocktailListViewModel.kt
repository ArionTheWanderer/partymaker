package com.example.partymaker.presentation.ui.parties.details.pager.cocktails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.interactors.IPartyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailListViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
): ViewModel() {
    private val _cocktailList = MutableStateFlow<DataState<List<CocktailDomain>>>(DataState.Init)

    val cocktailList: StateFlow<DataState<List<CocktailDomain>>>
        get() = _cocktailList

    fun getCocktailList(partyId: Long) = viewModelScope.launch {
        _cocktailList.value = DataState.Loading
        partyInteractor.getCocktailsBy(partyId).collect {
            _cocktailList.value = it
        }
    }

    fun deleteCocktail(cocktailId: Long, partyId: Long) = viewModelScope.launch {
        _cocktailList.value = DataState.Loading
        partyInteractor.deleteCocktail(cocktailId, partyId)
    }

    fun resetErrorMessage() {
        _cocktailList.value = DataState.Init
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }
}

private const val TAG = "CocktailListViewModel"
