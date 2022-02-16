package com.example.partymaker.presentation.di

import com.example.partymaker.presentation.di.party.PartyComponent
import dagger.Module

@Module(subcomponents = [PartyComponent::class])
class AppSubcomponentsModule
