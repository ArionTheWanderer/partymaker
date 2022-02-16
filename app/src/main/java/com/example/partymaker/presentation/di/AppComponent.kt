package com.example.partymaker.presentation.di

import android.app.Application
import com.example.partymaker.presentation.di.party.PartyComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelFactoryModule::class,
        AppSubcomponentsModule::class
    ]
)
interface AppComponent  {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun plusPartyComponent(): PartyComponent.Factory

}
