package com.example.partymaker.presentation.di.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.partymaker.presentation.ui.parties.dialogs.PartyDeleteDialogViewModel
import com.example.partymaker.presentation.ui.parties.details.PartyDetailsViewModel
import com.example.partymaker.presentation.ui.parties.dialogs.PartyDialogViewModel
import com.example.partymaker.presentation.ui.parties.list.PartyListViewModel
import com.example.partymaker.presentation.ui.cocktails.search.CocktailSearchViewModel
import com.example.partymaker.presentation.ui.meals.details.MealDetailsViewModel
import com.example.partymaker.presentation.ui.meals.search.MealSearchViewModel
import com.example.partymaker.presentation.viewmodels.PartyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindPartyViewModelFactory(factory: PartyViewModelFactory): ViewModelProvider.Factory

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

    @Binds
    @IntoMap
    @ViewModelKey(MealSearchViewModel::class)
    abstract fun bindMealSearchViewModel(mealSearchViewModel: MealSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CocktailSearchViewModel::class)
    abstract fun bindCocktailSearchViewModel(cocktailSearchViewModel: CocktailSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealDetailsViewModel::class)
    abstract fun bindMealDetailsViewModel(mealDetailsViewModel: MealDetailsViewModel): ViewModel

}
