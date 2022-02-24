package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.data.db.relations.MealWithIngredients
import com.example.partymaker.data.db.relations.PartyWithMeals
import com.example.partymaker.domain.common.EntityMapper
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.domain.entities.PartyDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyWithMealsEntityMapper
@Inject constructor(
    private val mealEntityMapper: MealEntityMapper
): EntityMapper<PartyDomain, PartyWithMeals> {
    override fun mapFromDomainModel(domainModel: PartyDomain): PartyWithMeals {
        val mealWithIngredientsList: MutableList<MealWithIngredients> = mutableListOf()
        domainModel.meals.forEach {
            val mealWithIngredients = mealEntityMapper.mapFromDomainModel(it)
            mealWithIngredientsList.add(mealWithIngredients)
        }
        val partyEntity = PartyEntity(
            partyId = domainModel.id,
            name = domainModel.name
        )
        return PartyWithMeals(
            party = partyEntity,
            mealWithIngredientsList = mealWithIngredientsList
        )
    }

    override fun mapToDomainModel(externalModel: PartyWithMeals): PartyDomain {
        val meals: MutableList<MealDomain> = mutableListOf()
        externalModel.mealWithIngredientsList.forEach {
            val mealDomain = mealEntityMapper.mapToDomainModel(it)
            meals.add(mealDomain)
        }
        return PartyDomain(
            id = externalModel.party.partyId,
            name = externalModel.party.name,
            meals = meals,
            cocktails = listOf()
        )
    }

}
