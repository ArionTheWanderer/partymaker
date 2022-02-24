package com.example.partymaker.presentation.ui.cocktails.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.interactors.ICocktailInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailDetailsViewModel
@Inject constructor(
    private val cocktailInteractor: ICocktailInteractor
): ViewModel() {
    private val _cocktail = MutableStateFlow<DataState<CocktailDomain>>(DataState.Init)
    private val _addResponse = MutableStateFlow<DataState<String>>(DataState.Init)
    private val _deleteResponse = MutableStateFlow<DataState<String>>(DataState.Init)

    val cocktail: StateFlow<DataState<CocktailDomain>>
        get() = _cocktail

    val addResponse: StateFlow<DataState<String>>
        get() = _addResponse

    val deleteResponse: StateFlow<DataState<String>>
        get() = _deleteResponse

    fun getCocktailById(cocktailId: Long, partyId: Long) = viewModelScope.launch {
        _cocktail.value = DataState.Loading
        val result = cocktailInteractor.getCocktailById(cocktailId, partyId)
        _cocktail.value = result
    }

    fun insertCocktail(cocktailId: Long, partyId: Long) = viewModelScope.launch {
        _addResponse.value = DataState.Loading
        val result = cocktailInteractor.insertCocktail(cocktailId, partyId)
        _addResponse.value = result
    }

    fun deleteCocktail(cocktailId: Long, partyId: Long) = viewModelScope.launch {
        _deleteResponse.value = DataState.Loading
        cocktailInteractor.deleteCocktail(cocktailId, partyId)
        _deleteResponse.value = DataState.Data("Deleted")
    }

    fun resetErrorMessage() {
        _cocktail.value = DataState.Init
    }

    fun resetAddResponse() {
        _addResponse.value = DataState.Init
    }

    fun resetDeleteResponse() {
        _deleteResponse.value = DataState.Init
    }
}
