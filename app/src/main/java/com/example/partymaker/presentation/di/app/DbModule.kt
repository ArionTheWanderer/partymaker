package com.example.partymaker.presentation.di.app

import android.app.Application
import androidx.room.Room
import com.example.partymaker.data.db.CocktailDao
import com.example.partymaker.data.db.MealDao
import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.PartyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {
    @JvmStatic
    @Singleton
    @Provides
    fun provideDb(app: Application): PartyDatabase {
        return Room.databaseBuilder(
            app,
            PartyDatabase::class.java,
            "party_database"
        ).fallbackToDestructiveMigration().build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun providePartyDao(db: PartyDatabase): PartyDao {
        return db.partyDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideMealDao(db: PartyDatabase): MealDao {
        return db.mealDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideCocktailDao(db: PartyDatabase): CocktailDao {
        return db.cocktailDao()
    }
}
