package com.example.partymaker.data.datasources

import com.example.partymaker.data.network.api.CocktailApi
import com.example.partymaker.data.network.response.CocktailResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface ICocktailRemoteDataSource {
    suspend fun getCocktailByName(name: String): Response<CocktailResponse>
    suspend fun getCocktailById(id: Long): Response<CocktailResponse>
}

@Singleton
class CocktailRemoteDataSource
@Inject constructor(
    private val cocktailApi: CocktailApi
): ICocktailRemoteDataSource {
    override suspend fun getCocktailByName(name: String): Response<CocktailResponse> =
        cocktailApi.getCocktailByName(name)

    override suspend fun getCocktailById(id: Long): Response<CocktailResponse> =
        cocktailApi.getCocktailById(id)
}
