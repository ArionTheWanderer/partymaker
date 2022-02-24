package com.example.partymaker.data.repositories

import com.example.partymaker.data.common.CocktailResponseMapper
import com.example.partymaker.data.datasources.ICocktailRemoteDataSource
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.repositories.ICocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepository
@Inject constructor(
    private val cocktailRemoteDataSource: ICocktailRemoteDataSource,
    private val cocktailResponseMapper: CocktailResponseMapper
): ICocktailRepository {

    private var lastFetchedCocktailListDomain: DataState<List<CocktailDomain>> = DataState.Init

    private val lastFetchedCocktailListDomainFlow = MutableSharedFlow<DataState<List<CocktailDomain>>>(
        replay = 0, // do not send events to new subscribers which have been emitted before subscription
        extraBufferCapacity = 1, // min. buffer capacity for using DROP_OLDEST overflow policy
        onBufferOverflow = BufferOverflow.DROP_OLDEST // newest item will replace oldest item in case of buffer overflow
    )

    override fun listenLastFetchedCocktailList(): Flow<DataState<List<CocktailDomain>>> =
        lastFetchedCocktailListDomainFlow

    override suspend fun getCocktailByName(name: String) = withContext(Dispatchers.IO){
        val cocktailByNameResponse = cocktailRemoteDataSource.getCocktailByName(name)
        if (cocktailByNameResponse.isSuccessful) {
            cocktailByNameResponse.body().let { cocktailResponse ->
                val cocktailListDomain: MutableList<CocktailDomain> = mutableListOf()
                cocktailResponse?.drinks?.forEach { cocktail ->
                    val cocktailDomain = cocktailResponseMapper.mapToDomainModel(cocktail)
                    cocktailListDomain.add(cocktailDomain)
                }
                if (cocktailListDomain.isNotEmpty()) {
                    lastFetchedCocktailListDomain = DataState.Data(cocktailListDomain)
                    lastFetchedCocktailListDomainFlow.emit(DataState.Data(cocktailListDomain))
                } else {
                    lastFetchedCocktailListDomain = DataState.Init
                    lastFetchedCocktailListDomainFlow.emit(DataState.Init)
                }
                return@withContext
            }
        }
        lastFetchedCocktailListDomain = DataState.Error("Network error. Code: ${cocktailByNameResponse.code()}")
        lastFetchedCocktailListDomainFlow.emit(DataState.Error("Network error. Code: ${cocktailByNameResponse.code()}"))
        return@withContext
    }

    override suspend fun filterResultsByAlcoholic(alcoholic: CocktailAlcoholicEnum) = withContext(Dispatchers.IO){
        if (lastFetchedCocktailListDomain is DataState.Data) {
            when (alcoholic) {
                CocktailAlcoholicEnum.All -> {
                    lastFetchedCocktailListDomainFlow.emit(lastFetchedCocktailListDomain)
                }
                else -> {
                    val filteredData =
                        (lastFetchedCocktailListDomain as DataState.Data<List<CocktailDomain>>)
                        .data
                        .filter { it.alcoholic == alcoholic }
                    lastFetchedCocktailListDomainFlow.emit(DataState.Data(filteredData))
                }
            }
        }
    }
}
