package com.example.partymaker.data.repositories

import com.example.partymaker.data.common.MealEntityMapper
import com.example.partymaker.data.common.MealResponseMapper
import com.example.partymaker.data.datasources.IMealLocalDataSource
import com.example.partymaker.data.datasources.IMealRemoteDataSource
import com.example.partymaker.data.db.entities.PartyMealCrossRef
import com.example.partymaker.data.network.response.MealResponse
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.repositories.IMealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository
@Inject constructor(
    private val mealRemoteDataSource: IMealRemoteDataSource,
    private val mealLocalDataSource: IMealLocalDataSource,
    private val mealResponseMapper: MealResponseMapper,
    private val mealEntityMapper: MealEntityMapper
) : IMealRepository {

    private var lastFetchedMealListDomain: DataState<List<MealDomain>> = DataState.Init

    private val lastFetchedMealListDomainFlow = MutableSharedFlow<DataState<List<MealDomain>>>(
        replay = 0, // do not send events to new subscribers which have been emitted before subscription
        extraBufferCapacity = 1, // min. buffer capacity for using DROP_OLDEST overflow policy
        onBufferOverflow = BufferOverflow.DROP_OLDEST // newest item will replace oldest item in case of buffer overflow
    )

    override fun listenLastFetchedMealList(): Flow<DataState<List<MealDomain>>> =
        lastFetchedMealListDomainFlow

    override suspend fun getMealByName(name: String) = withContext(Dispatchers.IO) {

        val mealByNameResponse: Response<MealResponse>
        try {
            mealByNameResponse = mealRemoteDataSource.getMealByName(name)
        } catch (e: Exception) {
            lastFetchedMealListDomain = DataState.Error("Network error")
            lastFetchedMealListDomainFlow.emit(DataState.Error("Network error"))
            return@withContext
        }
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

    override suspend fun getMealById(id: Long): DataState<MealDomain> = withContext(Dispatchers.IO){
        val mealByIdResponse: Response<MealResponse>
        try {
            mealByIdResponse = mealRemoteDataSource.getMealById(id)
        } catch (e: Exception) {
            val mealFromLocal = mealLocalDataSource.getMeal(id)
            if (mealFromLocal != null) {
                val mealDomain = mealEntityMapper.mapToDomainModel(mealFromLocal)
                return@withContext DataState.Data(mealDomain)
            } else {
                return@withContext DataState.Error("Network error. DB is empty")
            }
        }

        if (mealByIdResponse.isSuccessful) {
            mealByIdResponse.body().let { mealResponse ->
                val mealFromResponse = mealResponse?.meals?.get(0)
                if (mealFromResponse != null) {
                    val mealDomain = mealResponseMapper.mapToDomainModel(mealFromResponse)
                    return@withContext DataState.Data(mealDomain)
                } else {
                    return@withContext DataState.Error("Wrong id")
                }
            }
        }
        return@withContext DataState.Error("Network error: ${mealByIdResponse.code()}")
    }

    override suspend fun insertMeal(mealId: Long, partyId: Long): DataState<String> = withContext(Dispatchers.IO){
        val mealByIdResponse: Response<MealResponse>
        try {
            mealByIdResponse = mealRemoteDataSource.getMealById(mealId)
        } catch (e: Exception) {
            return@withContext DataState.Error("Network error. DB is empty")
        }

        if (mealByIdResponse.isSuccessful) {
            mealByIdResponse.body().let { mealResponse ->
                val mealFromResponse = mealResponse?.meals?.get(0)
                if (mealFromResponse != null) {
                    val mealDomain = mealResponseMapper.mapToDomainModel(mealFromResponse)
                    val mealWithIngredients = mealEntityMapper.mapFromDomainModel(mealDomain)
                    mealLocalDataSource.insertMeal(
                        mealEntity = mealWithIngredients.meal,
                        mealIngredientListEntity = mealWithIngredients.mealIngredientList,
                        partyMealCrossRef = PartyMealCrossRef(
                            partyId = partyId,
                            mealId = mealId
                        )
                    )
                    return@withContext DataState.Data("Inserted")
                } else {
                    return@withContext DataState.Error("Wrong id")
                }
            }
        }
        return@withContext DataState.Error("Network error: ${mealByIdResponse.code()}")

    }

    override suspend fun deleteMeal(mealId: Long, partyId: Long) = withContext(Dispatchers.IO) {
        mealLocalDataSource.deleteMeal(mealId, partyId)
    }

    override suspend fun filterResultsByCategory(category: MealCategoryEnum) =
        withContext(Dispatchers.IO) {
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
