package com.example.partymaker.domain.entities

data class PartyDomain(
    val id: Long,
    val name: String,
    val meals: List<MealDomain>,
    val cocktails: List<CocktailDomain>
)
