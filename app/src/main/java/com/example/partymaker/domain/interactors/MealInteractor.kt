package com.example.partymaker.domain.interactors

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.repositories.IMealRepository
import javax.inject.Inject

class MealInteractor
@Inject constructor(
    private val mealRepository: IMealRepository
): IMealInteractor {
    override suspend fun getMealByName(name: String): DataState<List<MealDomain>> =
        mealRepository.getMealByName(name)
}

interface IMealInteractor {
    suspend fun getMealByName(name: String): DataState<List<MealDomain>>
}
