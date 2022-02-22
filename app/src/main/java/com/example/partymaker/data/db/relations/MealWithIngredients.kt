package com.example.partymaker.data.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.partymaker.data.db.entities.MealEntity
import com.example.partymaker.data.db.entities.MealIngredientEntity

data class MealWithIngredients(
    @Embedded val meal: MealEntity,
    @Relation(
        parentColumn = "meal_id",
        entityColumn = "meal_parent_id"
    )
    val mealIngredientList: List<MealIngredientEntity>
)
