package com.example.partymaker.data.network.api

import com.example.partymaker.data.network.response.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("search.php")
    suspend fun getCocktailByName(@Query("s") name: String): Response<CocktailResponse>

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: Long): Response<CocktailResponse>
}
