package com.example.partymaker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.partymaker.data.db.entities.PartyEntity

@Database(entities = [PartyEntity::class], version = 1)
abstract class PartyDatabase: RoomDatabase() {
    abstract fun partyDao(): PartyDao

    companion object {

        const val DB_NAME = "party_database"

        /*@Volatile private var INSTANCE: PartyDatabase? = null

        fun getDatabase(context: Context): PartyDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PartyDatabase::class.java,
                    "party_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }*/
    }
}
