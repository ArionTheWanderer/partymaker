package com.example.partymaker.presentation.di.main.parties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.partymaker.presentation.viewmodels.PartyDialogViewModel
import com.example.partymaker.presentation.viewmodels.PartyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
abstract class PartyViewModelModule {

    @Binds
    @Named("partyViewModelFactory")
    abstract fun bindPartyViewModelFactory(factory: PartyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @PartyViewModelKey(PartyDialogViewModel::class)
    abstract fun bindPartyDialogViewModel(partyDialogViewModel: PartyDialogViewModel): ViewModel

}
