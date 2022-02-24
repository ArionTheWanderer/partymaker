package com.example.partymaker.presentation.ui.common

import androidx.appcompat.app.AppCompatActivity
import com.example.partymaker.presentation.ui.PartyApplication

open class BaseActivity: AppCompatActivity() {

    private val appComponent get() = (application as PartyApplication).appComponent

    val activityComponent by lazy {
        appComponent.getActivityComponentFactory().create(this)
    }

    private val presentationComponent by lazy {
        activityComponent.getPresentationComponentFactory().create()
    }

    protected val injector get() = presentationComponent
}
