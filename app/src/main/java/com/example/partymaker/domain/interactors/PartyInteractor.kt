package com.example.partymaker.domain.interactors

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.entities.PartyDomain
import com.example.partymaker.domain.repositories.IPartyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartyInteractor
@Inject constructor(
    private val partyRepository: IPartyRepository
): IPartyInteractor {
    override suspend fun createParty(party: PartyDomain): DataState<String> =
        partyRepository.insertParty(party)

    override suspend fun updateParty(party: PartyDomain): DataState<String> =
        partyRepository.updateParty(party)

    override suspend fun deleteParty(id: Long) =
        partyRepository.deleteParty(id)

    override suspend fun deleteMeal(mealId: Long, partyId: Long) =
        partyRepository.deleteMeal(mealId, partyId)

    override suspend fun deleteCocktail(cocktailId: Long, partyId: Long) =
        partyRepository.deleteCocktail(cocktailId, partyId)

    override fun getParty(id: Long): Flow<DataState<PartyDomain>> =
        partyRepository.getParty(id)

    override fun getPartyList(): Flow<DataState<List<PartyDomain>>> =
        partyRepository.getPartyList()

    override fun getMealsBy(partyId: Long): Flow<DataState<List<MealDomain>>> =
        partyRepository.getMealsBy(partyId)

    override fun getCocktailsBy(partyId: Long): Flow<DataState<List<CocktailDomain>>> =
        partyRepository.getCocktailsBy(partyId)

}

interface IPartyInteractor {
    suspend fun createParty(party: PartyDomain): DataState<String>
    suspend fun updateParty(party: PartyDomain): DataState<String>
    suspend fun deleteParty(id: Long)
    suspend fun deleteMeal(mealId: Long, partyId: Long)
    suspend fun deleteCocktail(cocktailId: Long, partyId: Long)
    fun getParty(id: Long): Flow<DataState<PartyDomain>>
    fun getPartyList(): Flow<DataState<List<PartyDomain>>>
    fun getMealsBy(partyId: Long): Flow<DataState<List<MealDomain>>>
    fun getCocktailsBy(partyId: Long): Flow<DataState<List<CocktailDomain>>>
}
