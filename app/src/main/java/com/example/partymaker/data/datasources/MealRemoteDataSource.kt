package com.example.partymaker.data.datasources

import com.example.partymaker.data.network.api.MealApi
import com.example.partymaker.data.network.response.MealResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface IMealRemoteDataSource {
    suspend fun getMealByName(name: String): Response<MealResponse>
    suspend fun getMealById(id: Long): Response<MealResponse>
}

@Singleton
class MealRemoteDataSource
@Inject constructor(
    private val mealApi: MealApi
): IMealRemoteDataSource {
    override suspend fun getMealByName(name: String): Response<MealResponse> =
        mealApi.getMealByName(name)

    override suspend fun getMealById(id: Long): Response<MealResponse> =
        mealApi.getMealById(id)
}
