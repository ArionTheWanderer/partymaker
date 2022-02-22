package com.example.partymaker.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.partymaker.data.db.entities.CocktailEntity
import com.example.partymaker.data.db.entities.PartyCocktailCrossRef
import com.example.partymaker.data.db.entities.PartyEntity

data class PartyWithCocktails(
    @Embedded val party: PartyEntity,
    @Relation(
        entity = CocktailEntity::class,
        parentColumn = "party_id",
        entityColumn = "cocktail_id",
        associateBy = Junction(PartyCocktailCrossRef::class)
    )
    val cocktailWithIngredientsList: List<CocktailWithIngredients>
)
