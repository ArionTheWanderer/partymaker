package com.example.partymaker.data.repositories

import com.example.partymaker.data.common.MealResponseMapper
import com.example.partymaker.data.datasources.IMealRemoteDataSource
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.repositories.IMealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository
@Inject constructor(
    private val mealRemoteDataSource: IMealRemoteDataSource,
    private val mealResponseMapper: MealResponseMapper
): IMealRepository {

    private var lastFetchedMealListDomain: DataState<List<MealDomain>> = DataState.Init

    private val lastFetchedMealListDomainFlow = MutableSharedFlow<DataState<List<MealDomain>>>(
        replay = 0, // do not send events to new subscribers which have been emitted before subscription
        extraBufferCapacity = 1, // min. buffer capacity for using DROP_OLDEST overflow policy
        onBufferOverflow = BufferOverflow.DROP_OLDEST // newest item will replace oldest item in case of buffer overflow
    )

    override fun listenLastFetchedMealList(): Flow<DataState<List<MealDomain>>> =
        lastFetchedMealListDomainFlow

    override suspend fun getMealByName(name: String) = withContext(Dispatchers.IO){
        val mealByNameResponse = mealRemoteDataSource.getMealByName(name)
        if (mealByNameResponse.isSuccessful) {
            mealByNameResponse.body().let { mealResponse ->
                val mealListDomain: MutableList<MealDomain> = mutableListOf()
                mealResponse?.meals?.forEach { meal ->
                    val mealDomain = mealResponseMapper.mapToDomainModel(meal)
                    mealListDomain.add(mealDomain)
                }
                if (mealListDomain.isNotEmpty()) {
                    lastFetchedMealListDomain = DataState.Data(mealListDomain)
                    lastFetchedMealListDomainFlow.emit(DataState.Data(mealListDomain))
                } else {
                    lastFetchedMealListDomain = DataState.Init
                    lastFetchedMealListDomainFlow.emit(DataState.Init)
                }
                return@withContext
            }
        }
        lastFetchedMealListDomain = DataState.Error("Network error: ${mealByNameResponse.code()}")
        lastFetchedMealListDomainFlow.emit(DataState.Error("Network error: ${mealByNameResponse.code()}"))
        return@withContext
    }

    override suspend fun filterResultsByCategory(category: MealCategoryEnum) = withContext(Dispatchers.IO){
        if (lastFetchedMealListDomain is DataState.Data) {
            when (category) {
                MealCategoryEnum.All -> {
                    lastFetchedMealListDomainFlow.emit(lastFetchedMealListDomain)
                }
                else -> {
                    val filteredData =
                        (lastFetchedMealListDomain as DataState.Data<List<MealDomain>>)
                        .data
                        .filter { it.category == category }
                    lastFetchedMealListDomainFlow.emit(DataState.Data(filteredData))
                }
            }
        }
    }
}
