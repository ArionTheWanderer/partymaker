package com.example.partymaker.data.db.entities

import androidx.room.*

@Entity(
    tableName = "cocktail_ingredient",
    foreignKeys =
    [
        ForeignKey(entity = CocktailEntity::class, parentColumns = ["cocktail_id"],
            childColumns = ["cocktail_parent_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices =
    [
        Index(value = ["cocktail_parent_id"])
    ]
)
data class CocktailIngredientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cocktail_ingredient_id")
    val cocktailIngredientId: Long,
    @ColumnInfo(name = "ingredient") val ingredient: String,
    @ColumnInfo(name = "measure") val measure: String,
    @ColumnInfo(name = "cocktail_parent_id") val cocktailParentId: Long
)
