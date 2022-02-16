package com.example.partymaker

import android.app.Application
import com.example.partymaker.presentation.di.Injector

class PartyApplication: Application() {
    override fun onCreate() {
        Injector.init(this)
        super.onCreate()
    }
}
