package com.example.partymaker.domain.interactors

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.repositories.IMealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealInteractor
@Inject constructor(
    private val mealRepository: IMealRepository
): IMealInteractor {
    override fun listenLastFetchedMealList(): Flow<DataState<List<MealDomain>>> =
        mealRepository.listenLastFetchedMealList()

    override suspend fun filterResultsByCategory(category: MealCategoryEnum) =
        mealRepository.filterResultsByCategory(category)


    override suspend fun getMealByName(name: String) =
        mealRepository.getMealByName(name)
}

interface IMealInteractor {
    fun listenLastFetchedMealList(): Flow<DataState<List<MealDomain>>>
    suspend fun filterResultsByCategory(category: MealCategoryEnum)
    suspend fun getMealByName(name: String)
}
