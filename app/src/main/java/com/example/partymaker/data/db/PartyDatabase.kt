package com.example.partymaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.partymaker.data.db.entities.*

@Database(
    entities =
    [
        PartyEntity::class, MealEntity::class, CocktailEntity::class,
        MealIngredientEntity::class, CocktailIngredientEntity::class,
        PartyMealCrossRef::class, PartyCocktailCrossRef::class
    ],
    version = 1
)
abstract class PartyDatabase: RoomDatabase() {
    abstract fun partyDao(): PartyDao
    abstract fun mealDao(): MealDao

    companion object {

        const val DB_NAME = "party_database"
    }
}
