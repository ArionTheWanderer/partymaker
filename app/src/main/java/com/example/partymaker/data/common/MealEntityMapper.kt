package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.MealEntity
import com.example.partymaker.data.db.entities.MealIngredientEntity
import com.example.partymaker.data.db.relations.MealWithIngredients
import com.example.partymaker.domain.common.EntityMapper
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealEntityMapper
@Inject constructor(): EntityMapper<MealDomain, MealWithIngredients> {
    override fun mapFromDomainModel(domainModel: MealDomain): MealWithIngredients {
        val meal = MealEntity(
            mealId = domainModel.mealId,
            name = domainModel.name,
            category = domainModel.category.name,
            area = domainModel.area,
            instructions = domainModel.instructions,
            thumbnailLink = domainModel.thumbnailLink,
            videoLink = domainModel.videoLink
        )
        val mealIngredients: MutableList<MealIngredientEntity> = mutableListOf()

        domainModel.ingredientsWithMeasures.forEach {
            mealIngredients.add(
                MealIngredientEntity(
                    mealIngredientId = 0,
                    ingredient = it.first,
                    measure = it.second,
                    mealParentId = domainModel.mealId
                )
            )
        }
        return MealWithIngredients(
            meal = meal,
            mealIngredientList = mealIngredients
        )
    }

    override fun mapToDomainModel(externalModel: MealWithIngredients): MealDomain {
        val ingredientsWithMeasures: MutableList<Pair<String, String>> = mutableListOf()
        externalModel.mealIngredientList.forEach {
            ingredientsWithMeasures.add(it.ingredient to it.measure)
        }

        return MealDomain(
            isInCurrentParty = false,
            mealId = externalModel.meal.mealId,
            name = externalModel.meal.name,
            category = MealCategoryEnum.valueOf(externalModel.meal.category),
            area = externalModel.meal.area,
            instructions = externalModel.meal.instructions,
            thumbnailLink = externalModel.meal.thumbnailLink,
            videoLink = externalModel.meal.videoLink,
            ingredientsWithMeasures = ingredientsWithMeasures
        )
    }

}