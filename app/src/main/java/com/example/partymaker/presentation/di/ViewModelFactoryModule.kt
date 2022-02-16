package com.example.partymaker.presentation.di

import androidx.lifecycle.ViewModelProvider
import com.example.partymaker.presentation.viewmodels.PartyViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindPartyViewModelFactory(factory: PartyViewModelFactory): ViewModelProvider.Factory
}
