package com.example.partymaker.presentation.ui

import android.app.Application
import com.example.partymaker.presentation.di.app.AppComponent
import com.example.partymaker.presentation.di.app.DaggerAppComponent

class PartyApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
