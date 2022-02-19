package com.example.partymaker.presentation.di.party

import androidx.lifecycle.ViewModel
import com.example.partymaker.presentation.di.ViewModelKey
import com.example.partymaker.presentation.ui.dialogs.PartyDeleteDialogViewModel
import com.example.partymaker.presentation.ui.parties.details.PartyDetailsViewModel
import com.example.partymaker.presentation.ui.dialogs.PartyDialogViewModel
import com.example.partymaker.presentation.ui.parties.PartyListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PartyViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PartyDeleteDialogViewModel::class)
    abstract fun bindPartyDeleteDialogViewModel(partyDeleteDialogViewModel: PartyDeleteDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PartyDialogViewModel::class)
    abstract fun bindPartyDialogViewModel(partyDialogViewModel: PartyDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PartyListViewModel::class)
    abstract fun bindPartyListViewModel(partyListViewModel: PartyListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PartyDetailsViewModel::class)
    abstract fun bindPartyDetailsViewModel(partyDetailsViewModel: PartyDetailsViewModel): ViewModel

}
