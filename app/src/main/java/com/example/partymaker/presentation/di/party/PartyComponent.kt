package com.example.partymaker.presentation.di.party

import com.example.partymaker.presentation.ui.PartyDeleteDialogFragment
import com.example.partymaker.presentation.ui.PartyDetailsFragment
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
    fun inject(partyDetailsFragment: PartyDetailsFragment)
    fun inject(partyDialogFragment: PartyDialogFragment)
    fun inject(partyDeleteDialogFragment: PartyDeleteDialogFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PartyComponent
    }
}
