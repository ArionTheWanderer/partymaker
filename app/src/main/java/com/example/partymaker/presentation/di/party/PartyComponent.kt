package com.example.partymaker.presentation.di.party

import com.example.partymaker.presentation.ui.PartyListFragment
import com.example.partymaker.presentation.ui.PartyDialogFragment
import dagger.Subcomponent

@PartyScope
@Subcomponent(
    modules = [
        PartyViewModelsModule::class,
        PartyModule::class
    ])
interface PartyComponent {
    fun inject(partyListFragment: PartyListFragment)
    fun inject(partyDialogFragment: PartyDialogFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PartyComponent
    }
}
