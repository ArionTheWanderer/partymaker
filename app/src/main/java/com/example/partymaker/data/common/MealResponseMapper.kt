package com.example.partymaker.data.common

import com.example.partymaker.data.network.response.Meal
import com.example.partymaker.domain.common.ResponseMapper
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.domain.entities.MealDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealResponseMapper
@Inject constructor(): ResponseMapper<MealDomain, Meal> {
    override fun mapToDomainModel(externalModel: Meal): MealDomain {
        val ingredientsWithMeasures: MutableList<Pair<String, String>> = mutableListOf()

        if (externalModel.ingredient1 != null && externalModel.ingredient1.isNotEmpty()
            && externalModel.measure1 != null && externalModel.measure1.isNotEmpty())
                ingredientsWithMeasures.add(externalModel.ingredient1 to externalModel.measure1)

        if (externalModel.ingredient2 != null && externalModel.ingredient2.isNotEmpty()
            && externalModel.measure2 != null && externalModel.measure2.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient2 to externalModel.measure2)

        if (externalModel.ingredient3 != null && externalModel.ingredient3.isNotEmpty()
            && externalModel.measure3 != null && externalModel.measure3.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient3 to externalModel.measure3)

        if (externalModel.ingredient4 != null && externalModel.ingredient4.isNotEmpty()
            && externalModel.measure4 != null && externalModel.measure4.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient4 to externalModel.measure4)

        if (externalModel.ingredient5 != null && externalModel.ingredient5.isNotEmpty()
            && externalModel.measure5 != null && externalModel.measure5.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient5 to externalModel.measure5)

        if (externalModel.ingredient6 != null && externalModel.ingredient6.isNotEmpty()
            && externalModel.measure6 != null && externalModel.measure6.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient6 to externalModel.measure6)

        if (externalModel.ingredient7 != null && externalModel.ingredient7.isNotEmpty()
            && externalModel.measure7 != null && externalModel.measure7.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient7 to externalModel.measure7)

        if (externalModel.ingredient8 != null && externalModel.ingredient8.isNotEmpty()
            && externalModel.measure8 != null && externalModel.measure8.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient8 to externalModel.measure8)

        if (externalModel.ingredient9 != null && externalModel.ingredient9.isNotEmpty()
            && externalModel.measure9 != null && externalModel.measure9.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient9 to externalModel.measure9)

        if (externalModel.ingredient10 != null && externalModel.ingredient10.isNotEmpty()
            && externalModel.measure10 != null && externalModel.measure10.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient10 to externalModel.measure10)

        if (externalModel.ingredient11 != null && externalModel.ingredient11.isNotEmpty()
            && externalModel.measure11 != null && externalModel.measure11.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient11 to externalModel.measure11)

        if (externalModel.ingredient12 != null && externalModel.ingredient12.isNotEmpty()
            && externalModel.measure12 != null && externalModel.measure12.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient12 to externalModel.measure12)

        if (externalModel.ingredient13 != null && externalModel.ingredient13.isNotEmpty()
            && externalModel.measure13 != null && externalModel.measure13.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient13 to externalModel.measure13)

        if (externalModel.ingredient14 != null && externalModel.ingredient14.isNotEmpty()
            && externalModel.measure14 != null && externalModel.measure14.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient14 to externalModel.measure14)

        if (externalModel.ingredient15 != null && externalModel.ingredient15.isNotEmpty()
            && externalModel.measure15 != null && externalModel.measure15.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient15 to externalModel.measure15)

        if (externalModel.ingredient16 != null && externalModel.ingredient16.isNotEmpty()
            && externalModel.measure16 != null && externalModel.measure16.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient16 to externalModel.measure16)

        if (externalModel.ingredient17 != null && externalModel.ingredient17.isNotEmpty()
            && externalModel.measure17 != null && externalModel.measure17.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient17 to externalModel.measure17)

        if (externalModel.ingredient18 != null && externalModel.ingredient18.isNotEmpty()
            && externalModel.measure18 != null && externalModel.measure18.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient18 to externalModel.measure18)

        if (externalModel.ingredient19 != null && externalModel.ingredient19.isNotEmpty()
            && externalModel.measure19 != null && externalModel.measure19.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient19 to externalModel.measure19)

        if (externalModel.ingredient20 != null && externalModel.ingredient20.isNotEmpty()
            && externalModel.measure20 != null && externalModel.measure20.isNotEmpty())
            ingredientsWithMeasures.add(externalModel.ingredient20 to externalModel.measure20)

        return MealDomain(
            mealId = externalModel.id.toLong(),
            name = externalModel.name,
            category = MealCategoryEnum.valueOf(externalModel.category),
            area = externalModel.area,
            instructions = externalModel.instructions,
            thumbnailLink = externalModel.thumbnailLink,
            videoLink = externalModel.video,
            ingredientsWithMeasures = ingredientsWithMeasures
        )
    }
}
