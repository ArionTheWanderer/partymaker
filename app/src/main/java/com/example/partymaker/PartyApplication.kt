package com.example.partymaker

import android.app.Application
import com.example.partymaker.presentation.di.app.AppComponent
import com.example.partymaker.presentation.di.DaggerAppComponent

class PartyApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}
