package com.example.partymaker.presentation.di.app

import com.example.partymaker.data.datasources.CocktailLocalDataSource
import com.example.partymaker.data.datasources.CocktailRemoteDataSource
import com.example.partymaker.data.datasources.ICocktailLocalDataSource
import com.example.partymaker.data.datasources.ICocktailRemoteDataSource
import com.example.partymaker.data.repositories.CocktailRepository
import com.example.partymaker.domain.interactors.CocktailInteractor
import com.example.partymaker.domain.interactors.ICocktailInteractor
import com.example.partymaker.domain.repositories.ICocktailRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CocktailModule {

    @Binds
    abstract fun provideCocktailRemoteDataSource(cocktailRemoteDataSource: CocktailRemoteDataSource): ICocktailRemoteDataSource

    @Binds
    abstract fun provideCocktailLocalDataSource(cocktailLocalDataSource: CocktailLocalDataSource): ICocktailLocalDataSource

    @Binds
    abstract fun provideCocktailRepository(cocktailRepository: CocktailRepository): ICocktailRepository

    @Binds
    abstract fun provideCocktailInteractor(cocktailInteractor: CocktailInteractor): ICocktailInteractor

}