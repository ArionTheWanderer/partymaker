package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey
    @ColumnInfo(name = "meal_id")
    val mealId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "instructions") val instructions: String,
    @ColumnInfo(name = "thumbnail_link") val thumbnailLink: String,
    @ColumnInfo(name = "video_link") val videoLink: String
)
