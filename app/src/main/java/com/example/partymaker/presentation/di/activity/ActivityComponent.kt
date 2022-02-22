package com.example.partymaker.presentation.di.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.partymaker.presentation.di.presentation.PresentationComponent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        PartyModule::class,
        NetworkModule::class
    ]
)
interface ActivityComponent {
    fun getPresentationComponentFactory(): PresentationComponent.Factory

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance activity: AppCompatActivity): ActivityComponent
    }
}
