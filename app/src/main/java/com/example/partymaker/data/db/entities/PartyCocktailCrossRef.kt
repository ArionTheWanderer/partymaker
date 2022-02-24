package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["party_id", "cocktail_id"], tableName = "party_cocktail")
data class PartyCocktailCrossRef(
    @ColumnInfo(name = "party_id") val partyId: Long,
    @ColumnInfo(name = "cocktail_id") val cocktailId: Long
)
