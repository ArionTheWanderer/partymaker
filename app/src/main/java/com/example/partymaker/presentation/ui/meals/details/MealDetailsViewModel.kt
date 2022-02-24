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
    private val _addResponse = MutableStateFlow<DataState<String>>(DataState.Init)
    private val _deleteResponse = MutableStateFlow<DataState<String>>(DataState.Init)

    val meal: StateFlow<DataState<MealDomain>>
        get() = _meal

    val addResponse: StateFlow<DataState<String>>
        get() = _addResponse

    val deleteResponse: StateFlow<DataState<String>>
        get() = _deleteResponse

    fun getMealById(mealId: Long, partyId: Long) = viewModelScope.launch {
        _meal.value = DataState.Loading
        val result = mealInteractor.getMealById(mealId, partyId)
        _meal.value = result
    }

    fun insertMeal(mealId: Long, partyId: Long) = viewModelScope.launch {
        _addResponse.value = DataState.Loading
        val result = mealInteractor.insertMeal(mealId, partyId)
        _addResponse.value = result
    }

    fun deleteMeal(mealId: Long, partyId: Long) = viewModelScope.launch {
        _deleteResponse.value = DataState.Loading
        mealInteractor.deleteMeal(mealId, partyId)
        _deleteResponse.value = DataState.Data("Deleted")
    }

    fun resetErrorMessage() {
        _meal.value = DataState.Init
    }

    fun resetAddResponse() {
        _addResponse.value = DataState.Init
    }

    fun resetDeleteResponse() {
        _deleteResponse.value = DataState.Init
    }
}
