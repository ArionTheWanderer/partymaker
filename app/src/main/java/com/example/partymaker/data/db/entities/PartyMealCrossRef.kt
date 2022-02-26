package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["party_id", "meal_id"],
    tableName = "party_meal",
    indices =
    [
        Index(value = ["meal_id"])
    ]
)
data class PartyMealCrossRef(
    @ColumnInfo(name = "party_id") val partyId: Long,
    @ColumnInfo(name = "meal_id") val mealId: Long
)
