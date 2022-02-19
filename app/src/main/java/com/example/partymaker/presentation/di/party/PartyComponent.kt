package com.example.partymaker.presentation.di.party

import com.example.partymaker.presentation.ui.dialogs.PartyDeleteDialogFragment
import com.example.partymaker.presentation.ui.parties.details.PartyDetailsFragment
import com.example.partymaker.presentation.ui.parties.PartyListFragment
import com.example.partymaker.presentation.ui.dialogs.PartyDialogFragment
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
