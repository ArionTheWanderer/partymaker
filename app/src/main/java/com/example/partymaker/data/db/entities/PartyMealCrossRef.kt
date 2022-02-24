package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["party_id", "meal_id"], tableName = "party_meal")
data class PartyMealCrossRef(
    @ColumnInfo(name = "party_id") val partyId: Long,
    @ColumnInfo(name = "meal_id") val mealId: Long
)
