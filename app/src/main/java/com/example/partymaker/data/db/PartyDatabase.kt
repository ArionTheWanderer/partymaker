package com.example.partymaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.partymaker.data.db.entities.PartyEntity

@Database(entities = [PartyEntity::class], version = 1)
abstract class PartyDatabase: RoomDatabase() {
    abstract fun partyDao(): PartyDao

    companion object {

        const val DB_NAME = "party_database"
    }
}
