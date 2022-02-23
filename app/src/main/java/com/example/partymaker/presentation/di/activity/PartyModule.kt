package com.example.partymaker.presentation.di.activity

import com.example.partymaker.data.datasources.IPartyLocalDataSource
import com.example.partymaker.data.datasources.PartyLocalDataSource
import com.example.partymaker.data.repositories.PartyRepository
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.domain.interactors.*
import dagger.Binds
import dagger.Module

@Module
abstract class PartyModule {

    @Binds
    abstract fun providePartyRepository(partyRepository: PartyRepository): IPartyRepository

    @Binds
    abstract fun providePartyInteractor(partyInteractor: PartyInteractor): IPartyInteractor

    @Binds
    abstract fun providePartyLocalDataSource(partyLocalDataSource: PartyLocalDataSource): IPartyLocalDataSource

}
