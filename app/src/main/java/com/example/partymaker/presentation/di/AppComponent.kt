package com.example.partymaker.presentation.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppSubcomponentsModule::class
        //SubComponentsModule::class
    ]
)
interface AppComponent  {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // fun inject(baseActivity: BaseActivity)

    // fun authComponent(): AuthComponent.Factory

    // fun mainComponent(): MainComponent.Factory

}
