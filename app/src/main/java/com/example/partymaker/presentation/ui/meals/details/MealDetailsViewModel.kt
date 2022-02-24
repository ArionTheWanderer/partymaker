package com.example.partymaker.presentation.ui.meals.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.interactors.IMealInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealDetailsViewModel
@Inject constructor(
    private val mealInteractor: IMealInteractor
): ViewModel() {
    private val _meal = MutableStateFlow<DataState<MealDomain>>(DataState.Init)

    val meal: StateFlow<DataState<MealDomain>>
        get() = _meal

    fun getMealById(id: Long) = viewModelScope.launch {
        _meal.value = DataState.Loading
        val result = mealInteractor.getMealById(id)
        _meal.value = result
    }

    fun resetErrorMessage() {
        _meal.value = DataState.Init
    }
}
