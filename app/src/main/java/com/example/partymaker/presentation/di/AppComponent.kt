package com.example.partymaker.presentation.di

import android.app.Application
import com.example.partymaker.presentation.di.activity.ActivityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent  {

    fun getActivityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AppComponent
    }

}
