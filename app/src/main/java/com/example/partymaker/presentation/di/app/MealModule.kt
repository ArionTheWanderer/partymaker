package com.example.partymaker.presentation.di.app


import com.example.partymaker.data.datasources.IMealRemoteDataSource
import com.example.partymaker.data.datasources.MealRemoteDataSource
import com.example.partymaker.data.repositories.MealRepository
import com.example.partymaker.domain.interactors.IMealInteractor
import com.example.partymaker.domain.interactors.MealInteractor
import com.example.partymaker.domain.repositories.IMealRepository
import dagger.Binds
import dagger.Module

@Module
abstract class MealModule {

    @Binds
    abstract fun provideMealRemoteDataSource(mealRemoteDataSource: MealRemoteDataSource): IMealRemoteDataSource

    @Binds
    abstract fun provideMealRepository(mealRepository: MealRepository): IMealRepository

    @Binds
    abstract fun provideMealInteractor(mealInteractor: MealInteractor): IMealInteractor

}