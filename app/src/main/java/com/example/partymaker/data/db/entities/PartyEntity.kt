package com.example.partymaker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "party",
    indices =
    [
        Index(value = ["name"], unique = true)
    ]
)
data class PartyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "party_id")
    val partyId: Long,
    @ColumnInfo(name = "name") val name: String
)
