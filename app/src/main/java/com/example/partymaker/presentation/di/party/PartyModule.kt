package com.example.partymaker.presentation.di.party

import com.example.partymaker.data.datasources.IPartyLocalDataSource
import com.example.partymaker.data.datasources.PartyLocalDataSource
import com.example.partymaker.data.repositories.PartyRepository
import com.example.partymaker.domain.repositories.IPartyRepository
import com.example.partymaker.domain.usecases.*
import dagger.Binds
import dagger.Module

@Module
abstract class PartyModule {

    @Binds
    abstract fun providePartyRepository(partyRepository: PartyRepository): IPartyRepository

    @Binds
    abstract fun provideCreatePartyUseCase(createPartyUseCase: CreatePartyUseCase): ICreatePartyUseCase

    @Binds
    abstract fun provideUpdatePartyNameUseCase(updatePartyNameUseCase: UpdatePartyNameUseCase): IUpdatePartyNameUseCase

    @Binds
    abstract fun provideGetPartyUseCase(getPartyUseCase: GetPartyUseCase): IGetPartyUseCase

    @Binds
    abstract fun provideGetPartyListUseCase(getPartyListUseCase: GetPartyListUseCase): IGetPartyListUseCase

    @Binds
    abstract fun providePartyLocalDataSource(partyLocalDataSource: PartyLocalDataSource): IPartyLocalDataSource
}
