package com.example.partymaker.presentation.di.main

import com.example.partymaker.presentation.ui.MainActivity
import dagger.Subcomponent

@MainActivityScope
@Subcomponent(modules = [MainSubcomponentsModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)

    @Subcomponent.Factory
    interface Factory{

        fun create(): MainActivityComponent
    }
}
