package com.example.partymaker.domain.entities

data class CocktailDomain(
    var isInCurrentParty: Boolean,
    val cocktailId: Long,
    val name: String,
    val videoLink: String?,
    val category: String,
    val alcoholic: CocktailAlcoholicEnum,
    val glass: String,
    val instructions: String,
    val thumbnailLink: String,
    val ingredientsWithMeasures: List<Pair<String, String>>
)
