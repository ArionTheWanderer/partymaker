package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.data.db.relations.CocktailWithIngredients
import com.example.partymaker.data.db.relations.PartyWithCocktails
import com.example.partymaker.domain.common.EntityMapper
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.entities.PartyDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyWithCocktailsEntityMapper
@Inject constructor(
    private val cocktailEntityMapper: CocktailEntityMapper
): EntityMapper<PartyDomain, PartyWithCocktails> {
    override fun mapFromDomainModel(domainModel: PartyDomain): PartyWithCocktails {
        val cocktailWithIngredientsList: MutableList<CocktailWithIngredients> = mutableListOf()
        domainModel.cocktails.forEach {
            val cocktailWithIngredients = cocktailEntityMapper.mapFromDomainModel(it)
            cocktailWithIngredientsList.add(cocktailWithIngredients)
        }
        val partyEntity = PartyEntity(
            partyId = domainModel.id,
            name = domainModel.name,
        )
        return PartyWithCocktails(
            party = partyEntity,
            cocktailWithIngredientsList = cocktailWithIngredientsList
        )
    }

    override fun mapToDomainModel(externalModel: PartyWithCocktails): PartyDomain {
        val cocktails: MutableList<CocktailDomain> = mutableListOf()
        externalModel.cocktailWithIngredientsList.forEach {
            val cocktail = cocktailEntityMapper.mapToDomainModel(it)
            cocktails.add(cocktail)
        }
        return PartyDomain(
            id = externalModel.party.partyId,
            name = externalModel.party.name,
            cocktails = cocktails,
            meals = listOf()
        )
    }
}
