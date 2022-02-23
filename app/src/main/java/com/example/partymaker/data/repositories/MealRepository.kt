package com.example.partymaker.data.repositories

import com.example.partymaker.data.common.MealResponseMapper
import com.example.partymaker.data.datasources.IMealRemoteDataSource
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.repositories.IMealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository
@Inject constructor(
    private val mealRemoteDataSource: IMealRemoteDataSource,
    private val mealResponseMapper: MealResponseMapper
): IMealRepository {

    private var lastMealListDomain: DataState<List<MealDomain>> = DataState.Init

    override suspend fun getMealByName(name: String): DataState<List<MealDomain>> = withContext(Dispatchers.IO){
        val mealByNameResponse = mealRemoteDataSource.getMealByName(name)
        if (mealByNameResponse.isSuccessful) {
            mealByNameResponse.body().let { mealResponse ->
                val mealListDomain: MutableList<MealDomain> = mutableListOf()
                mealResponse?.meals?.forEach { meal ->
                    val mealDomain = mealResponseMapper.mapToDomainModel(meal)
                    mealListDomain.add(mealDomain)
                }
                lastMealListDomain = DataState.Data(mealListDomain)
                return@withContext lastMealListDomain
            }
        }
        return@withContext DataState.Error("Network error: ${mealByNameResponse.code()}")
    }
}
