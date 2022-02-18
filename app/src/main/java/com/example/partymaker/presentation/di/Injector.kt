package com.example.partymaker.presentation.di

import android.app.Application
import android.util.Log
import com.example.partymaker.presentation.di.party.PartyComponent

object Injector {
    private lateinit var applicationComponent: AppComponent
    private var partyComponent: PartyComponent? = null
    private const val TAG = "Injector"

    fun init(app: Application) {
        applicationComponent = DaggerAppComponent.builder().application(app).build()
    }

    fun partyComponent(): PartyComponent? {
        Log.e(TAG, "partyComponent: get")
        if (partyComponent == null) {
            Log.e(TAG, "partyComponent: creation")
            partyComponent = applicationComponent.plusPartyComponent().create()
        }

        return partyComponent
    }

    fun clearPartyComponent() {
        partyComponent = null
        Log.e(TAG, "partyComponent: deletion")
    }
}
