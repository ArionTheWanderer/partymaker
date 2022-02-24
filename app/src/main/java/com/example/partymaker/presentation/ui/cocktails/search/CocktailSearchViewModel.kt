package com.example.partymaker.presentation.ui.cocktails.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.interactors.ICocktailInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailSearchViewModel
@Inject constructor(
    private val cocktailInteractor: ICocktailInteractor
): ViewModel() {
    private val _cocktailList = MutableStateFlow<DataState<List<CocktailDomain>>>(DataState.Init)

    val cocktailList: StateFlow<DataState<List<CocktailDomain>>>
        get() = _cocktailList

    init {
        viewModelScope.launch {
            cocktailInteractor.listenLastFetchedCocktailList().collect {
                Log.d(TAG, "fetched: $it")
                _cocktailList.value = it
            }
        }
    }

    fun getCocktailByName(name: String) = viewModelScope.launch {
        _cocktailList.value = DataState.Loading
        cocktailInteractor.getCocktailByName(name)
    }

    fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum) = viewModelScope.launch{
        _cocktailList.value = DataState.Loading
        cocktailInteractor.filterResultsByAlcoholic(alcoholic)
    }

    fun resetErrorMessage() {
        _cocktailList.value = DataState.Init
    }
}

private const val TAG = "CocktailSearchViewModel"
