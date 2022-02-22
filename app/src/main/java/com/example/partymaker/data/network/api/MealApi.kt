package com.example.partymaker.data.network.api

import com.example.partymaker.data.network.response.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("search.php")
    suspend fun getMealByName(@Query("s") name: String): Response<MealResponse>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Long): Response<MealResponse>
}
