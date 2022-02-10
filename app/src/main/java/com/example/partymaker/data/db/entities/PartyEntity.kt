package com.example.partymaker.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "party")
data class PartyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)
