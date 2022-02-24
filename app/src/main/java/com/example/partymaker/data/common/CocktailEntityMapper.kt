package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.CocktailEntity
import com.example.partymaker.data.db.entities.CocktailIngredientEntity
import com.example.partymaker.data.db.relations.CocktailWithIngredients
import com.example.partymaker.domain.common.EntityMapper
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailEntityMapper
@Inject constructor(): EntityMapper<CocktailDomain, CocktailWithIngredients> {
    override fun mapFromDomainModel(domainModel: CocktailDomain): CocktailWithIngredients {
        val cocktail = CocktailEntity(
            cocktailId = domainModel.cocktailId,
            name = domainModel.name,
            category = domainModel.category,
            alcoholic = CocktailAlcoholicEnum.enumToString(domainModel.alcoholic),
            glass = domainModel.glass,
            instructions = domainModel.instructions,
            thumbnailLink = domainModel.thumbnailLink,
            videoLink = domainModel.videoLink
        )
        val cocktailIngredients: MutableList<CocktailIngredientEntity> = mutableListOf()

        domainModel.ingredientsWithMeasures.forEach {
            cocktailIngredients.add(
                CocktailIngredientEntity(
                    cocktailIngredientId = 0,
                    ingredient = it.first,
                    measure = it.second,
                    cocktailParentId = domainModel.cocktailId
                )
            )
        }
        return CocktailWithIngredients(
            cocktail = cocktail,
            cocktailIngredientList = cocktailIngredients
        )
    }

    override fun mapToDomainModel(externalModel: CocktailWithIngredients): CocktailDomain {
        val ingredientsWithMeasures: MutableList<Pair<String, String>> = mutableListOf()
        externalModel.cocktailIngredientList.forEach {
            ingredientsWithMeasures.add(it.ingredient to it.measure)
        }

        return CocktailDomain(
            isInCurrentParty = false,
            cocktailId = externalModel.cocktail.cocktailId,
            name = externalModel.cocktail.name,
            videoLink = externalModel.cocktail.videoLink,
            category = externalModel.cocktail.category,
            alcoholic = CocktailAlcoholicEnum.getEnumByString(externalModel.cocktail.alcoholic),
            glass = externalModel.cocktail.glass,
            instructions = externalModel.cocktail.instructions,
            thumbnailLink = externalModel.cocktail.thumbnailLink,
            ingredientsWithMeasures = ingredientsWithMeasures
        )
    }
}
