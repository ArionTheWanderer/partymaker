package com.example.partymaker.presentation.ui.meals.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.interactors.IMealInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealSearchViewModel
@Inject constructor(
    private val mealInteractor: IMealInteractor
): ViewModel() {
    private val _mealList = MutableStateFlow<DataState<List<MealDomain>>>(DataState.Init)

    val mealList: StateFlow<DataState<List<MealDomain>>>
        get() = _mealList

    init {
        viewModelScope.launch {
            mealInteractor.listenLastFetchedMealList().collect {
                Log.d(TAG, "fetched: $it")
                _mealList.value = it
            }
        }
    }

    fun getMealByName(name: String) = viewModelScope.launch {
        _mealList.value = DataState.Loading
        mealInteractor.getMealByName(name)
    }

    fun filterResultsByCategory(category: MealCategoryEnum) = viewModelScope.launch{
        _mealList.value = DataState.Loading
        mealInteractor.filterResultsByCategory(category)
    }

    fun resetErrorMessage() {
        _mealList.value = DataState.Init
    }
}

private const val TAG = "MealSearchViewModel"
