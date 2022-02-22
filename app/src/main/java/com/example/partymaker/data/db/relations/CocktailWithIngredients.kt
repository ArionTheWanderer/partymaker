package com.example.partymaker.data.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.partymaker.data.db.entities.CocktailEntity
import com.example.partymaker.data.db.entities.CocktailIngredientEntity

data class CocktailWithIngredients(
    @Embedded val cocktail: CocktailEntity,
    @Relation(
        parentColumn = "cocktail_id",
        entityColumn = "cocktail_parent_id"
    )
    val cocktailIngredientList: List<CocktailIngredientEntity>
)
