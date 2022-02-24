package com.example.partymaker.domain.interactors

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.repositories.ICocktailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CocktailInteractor
@Inject constructor(
    private val cocktailRepository: ICocktailRepository
): ICocktailInteractor {
    override fun listenLastFetchedCocktailList(): Flow<DataState<List<CocktailDomain>>> =
        cocktailRepository.listenLastFetchedCocktailList()

    override suspend fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum) =
        cocktailRepository.filterResultsByAlcoholic(alcoholic)


    override suspend fun getCocktailByName(name: String) =
        cocktailRepository.getCocktailByName(name)
}

interface ICocktailInteractor {
    fun listenLastFetchedCocktailList(): Flow<DataState<List<CocktailDomain>>>
    suspend fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum)
    suspend fun getCocktailByName(name: String)
}
