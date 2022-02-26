package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["party_id", "cocktail_id"],
    tableName = "party_cocktail",
    indices =
    [
        Index(value = ["cocktail_id"])
    ]
)
data class PartyCocktailCrossRef(
    @ColumnInfo(name = "party_id") val partyId: Long,
    @ColumnInfo(name = "cocktail_id") val cocktailId: Long
)
