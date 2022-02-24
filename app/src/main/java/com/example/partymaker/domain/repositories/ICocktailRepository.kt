package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import kotlinx.coroutines.flow.Flow

interface ICocktailRepository {
    fun listenLastFetchedCocktailList(): Flow<DataState<List<CocktailDomain>>>
    suspend fun getCocktailByName(name: String)
    suspend fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum)
}