package com.example.partymaker.presentation.di.main

import com.example.partymaker.presentation.di.main.parties.PartyComponent
import dagger.Module

@Module(subcomponents = [PartyComponent::class])
class MainSubcomponentsModule