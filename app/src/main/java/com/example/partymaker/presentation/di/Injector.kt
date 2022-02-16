package com.example.partymaker.presentation.di

import android.app.Application
import com.example.partymaker.presentation.di.party.PartyComponent

object Injector {
    private lateinit var applicationComponent: AppComponent
    private var partyComponent: PartyComponent? = null

    fun init(app: Application) {
        applicationComponent = DaggerAppComponent.builder().application(app).build()
    }

    fun partyComponent(): PartyComponent? {
        if (partyComponent == null) {
            partyComponent = applicationComponent.plusPartyComponent().create()
        }

        return partyComponent
    }

    fun clearPartyComponent() {
        partyComponent = null
    }
}
