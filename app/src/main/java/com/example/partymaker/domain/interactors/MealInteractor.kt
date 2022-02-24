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

    override suspend fun getMealById(mealId: Long, partyId: Long): DataState<MealDomain> =
        mealRepository.getMealById(mealId, partyId)

    override suspend fun insertMeal(mealId: Long, partyId: Long): DataState<String> =
        mealRepository.insertMeal(mealId, partyId)

    override suspend fun deleteMeal(mealId: Long, partyId: Long) =
        mealRepository.deleteMeal(mealId, partyId)
}

interface IMealInteractor {
    fun listenLastFetchedMealList(): Flow<DataState<List<MealDomain>>>
    suspend fun filterResultsByCategory(category: MealCategoryEnum)
    suspend fun getMealByName(name: String)
    suspend fun getMealById(mealId: Long, partyId: Long): DataState<MealDomain>
    suspend fun insertMeal(mealId: Long, partyId: Long): DataState<String>
    suspend fun deleteMeal(mealId: Long, partyId: Long)
}
