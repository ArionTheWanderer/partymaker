package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import kotlinx.coroutines.flow.Flow

interface IMealRepository {
    fun listenLastFetchedMealList(): Flow<DataState<List<MealDomain>>>
    suspend fun getMealByName(name: String)
    suspend fun filterResultsByCategory(category: MealCategoryEnum)
    suspend fun getMealById(id: Long): DataState<MealDomain>
    suspend fun insertMeal(mealId: Long, partyId: Long): DataState<String>
    suspend fun deleteMeal(mealId: Long, partyId: Long)
}
