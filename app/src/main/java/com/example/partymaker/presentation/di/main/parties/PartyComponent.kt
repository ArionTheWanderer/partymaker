package com.example.partymaker.presentation.di.main.parties

import com.example.partymaker.presentation.ui.PartiesFragment
import com.example.partymaker.presentation.ui.PartyDialogFragment
import dagger.Subcomponent

@PartyScope
@Subcomponent(
    modules = [
        PartyViewModelModule::class,
        PartyModule::class,

    ])
interface PartyComponent {
    fun inject(partiesFragment: PartiesFragment)
    fun inject(partyDialogFragment: PartyDialogFragment)

    @Subcomponent.Factory
    interface Factory{

        fun create(): PartyComponent
    }
}
