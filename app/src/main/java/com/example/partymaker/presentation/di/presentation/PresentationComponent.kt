package com.example.partymaker.presentation.di.presentation

import com.example.partymaker.presentation.ui.parties.dialogs.PartyDeleteDialogFragment
import com.example.partymaker.presentation.ui.parties.dialogs.PartyDialogFragment
import com.example.partymaker.presentation.ui.parties.PartyListFragment
import com.example.partymaker.presentation.ui.parties.details.PartyDetailsFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(
    modules = [
        ViewModelsModule::class
    ]
)

interface PresentationComponent {
    fun inject(partyListFragment: PartyListFragment)
    fun inject(partyDetailsFragment: PartyDetailsFragment)
    fun inject(partyDialogFragment: PartyDialogFragment)
    fun inject(partyDeleteDialogFragment: PartyDeleteDialogFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PresentationComponent
    }
}
