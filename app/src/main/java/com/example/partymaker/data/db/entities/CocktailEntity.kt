package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktail")
data class CocktailEntity(
    @PrimaryKey
    @ColumnInfo(name = "cocktail_id")
    val cocktailId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "alcoholic") val alcoholic: String,
    @ColumnInfo(name = "glass") val glass: String,
    @ColumnInfo(name = "instructions") val instructions: String,
    @ColumnInfo(name = "thumbnail_link") val thumbnailLink: String,
    @ColumnInfo(name = "video_link") val videoLink: String
)
