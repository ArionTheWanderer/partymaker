package com.example.partymaker.data.datasources

import com.example.partymaker.data.network.api.MealApi
import com.example.partymaker.data.network.response.MealResponse
import com.example.partymaker.presentation.di.activity.ActivityScope
import retrofit2.Response
import javax.inject.Inject

interface IMealRemoteDataSource {
    suspend fun getMealByName(name: String): Response<MealResponse>
}

@ActivityScope
class MealRemoteDataSource
@Inject constructor(
    private val mealApi: MealApi
): IMealRemoteDataSource {
    override suspend fun getMealByName(name: String): Response<MealResponse> =
        mealApi.getMealByName(name)

}
