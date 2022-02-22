package com.example.partymaker.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.partymaker.data.db.entities.MealEntity
import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.data.db.entities.PartyMealCrossRef

data class PartyWithMeals(
    @Embedded val party: PartyEntity,
    @Relation(
        entity = MealEntity::class,
        parentColumn = "party_id",
        entityColumn = "meal_id",
        associateBy = Junction(PartyMealCrossRef::class)
    )
    val mealWithIngredientsList: List<MealWithIngredients>
)
