package com.example.partymaker.presentation.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.PartyDatabase
import com.example.partymaker.presentation.viewmodels.PartyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
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
}
