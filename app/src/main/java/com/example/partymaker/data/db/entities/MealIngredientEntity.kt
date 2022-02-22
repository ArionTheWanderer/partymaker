package com.example.partymaker.data.db.entities

import androidx.room.*

@Entity(
    tableName = "meal_ingredient",
    foreignKeys =
    [
        ForeignKey(entity = MealEntity::class, parentColumns = ["meal_id"],
            childColumns = ["meal_parent_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices =
    [
        Index(value = ["meal_parent_id"])
    ]
)
data class MealIngredientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meal_ingredient_id")
    val mealIngredientId: Long,
    @ColumnInfo(name = "ingredient") val ingredient: String,
    @ColumnInfo(name = "measure") val measure: String,
    @ColumnInfo(name = "meal_parent_id") val mealParentId: Long
)
