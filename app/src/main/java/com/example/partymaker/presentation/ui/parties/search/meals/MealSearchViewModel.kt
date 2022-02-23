package com.example.partymaker.presentation.ui.parties.search.meals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.entities.PartyDomain
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

    fun getMealByName(name: String) = viewModelScope.launch {
        _mealList.value = DataState.Loading
        val response = mealInteractor.getMealByName(name)
        if (response is DataState.Data && response.data.isNotEmpty()) {
            Log.d(TAG, "getMealByName: ${response.data[0].name}")
        }
        _mealList.value = response
    }

    fun resetErrorMessage() {
        _mealList.value = DataState.Init
    }
}

private const val TAG = "MealSearchViewModel"