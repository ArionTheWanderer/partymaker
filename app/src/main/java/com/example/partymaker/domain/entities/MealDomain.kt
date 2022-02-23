package com.example.partymaker.domain.entities

data class MealDomain(
    val mealId: Long,
    val name: String,
    val category: MealCategoryEnum,
    val area: String,
    val instructions: String,
    val thumbnailLink: String,
    val videoLink: String?,
    val ingredientsWithMeasures: List<Pair<String, String>>
)
