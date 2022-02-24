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

    override suspend fun getCocktailById(
        cocktailId: Long,
        partyId: Long
    ): DataState<CocktailDomain> =
        cocktailRepository.getCocktailById(cocktailId, partyId)

    override suspend fun insertCocktail(cocktailId: Long, partyId: Long): DataState<String> =
        cocktailRepository.insertCocktail(cocktailId, partyId)

    override suspend fun deleteCocktail(cocktailId: Long, partyId: Long) =
        cocktailRepository.deleteCocktail(cocktailId, partyId)
}

interface ICocktailInteractor {
    fun listenLastFetchedCocktailList(): Flow<DataState<List<CocktailDomain>>>
    suspend fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum)
    suspend fun getCocktailByName(name: String)
    suspend fun getCocktailById(cocktailId: Long, partyId: Long): DataState<CocktailDomain>
    suspend fun insertCocktail(cocktailId: Long, partyId: Long): DataState<String>
    suspend fun deleteCocktail(cocktailId: Long, partyId: Long)
}
