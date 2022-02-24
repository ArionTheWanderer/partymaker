package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.entities.PartyDomain
import kotlinx.coroutines.flow.Flow

interface IPartyRepository {
    suspend fun insertParty(party: PartyDomain): DataState<String>
    suspend fun updateParty(party: PartyDomain): DataState<String>
    suspend fun deleteParty(id: Long)
    suspend fun deleteMeal(mealId: Long, partyId: Long)
    suspend fun deleteCocktail(cocktailId: Long, partyId: Long)
    fun getParty(id: Long): Flow<DataState<PartyDomain>>
    fun getPartyList(): Flow<DataState<List<PartyDomain>>>
    fun getMealsBy(partyId: Long): Flow<DataState<List<MealDomain>>>
    fun getCocktailsBy(partyId: Long): Flow<DataState<List<CocktailDomain>>>
}
