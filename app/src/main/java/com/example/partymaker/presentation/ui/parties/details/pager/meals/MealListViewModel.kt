package com.example.partymaker.presentation.ui.parties.details.pager.meals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.interactors.IPartyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealListViewModel
@Inject constructor(
    private val partyInteractor: IPartyInteractor
): ViewModel() {
    private val _mealList = MutableStateFlow<DataState<List<MealDomain>>>(DataState.Init)

    val mealList: StateFlow<DataState<List<MealDomain>>>
        get() = _mealList

    fun getMealList(partyId: Long) = viewModelScope.launch {
        _mealList.value = DataState.Loading
        partyInteractor.getMealsBy(partyId).collect {
            _mealList.value = it
        }
    }

    fun deleteMeal(mealId: Long, partyId: Long) = viewModelScope.launch {
        _mealList.value = DataState.Loading
        partyInteractor.deleteMeal(mealId, partyId)
    }

    fun resetErrorMessage() {
        _mealList.value = DataState.Init
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }
}

private const val TAG = "MealListViewModel"
