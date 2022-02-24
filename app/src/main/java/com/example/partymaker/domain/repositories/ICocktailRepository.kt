package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import kotlinx.coroutines.flow.Flow

interface ICocktailRepository {
    fun listenLastFetchedCocktailList(): Flow<DataState<List<CocktailDomain>>>
    suspend fun getCocktailByName(name: String)
    suspend fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum)
    suspend fun getCocktailById(cocktailId: Long, partyId: Long): DataState<CocktailDomain>
    suspend fun insertCocktail(cocktailId: Long, partyId: Long): DataState<String>
    suspend fun deleteCocktail(cocktailId: Long, partyId: Long)
}