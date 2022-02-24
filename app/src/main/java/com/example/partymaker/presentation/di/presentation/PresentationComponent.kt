package com.example.partymaker.presentation.di.presentation

import com.example.partymaker.presentation.ui.cocktails.details.CocktailDetailsFragment
import com.example.partymaker.presentation.ui.parties.dialogs.PartyDeleteDialogFragment
import com.example.partymaker.presentation.ui.parties.dialogs.PartyDialogFragment
import com.example.partymaker.presentation.ui.parties.list.PartyListFragment
import com.example.partymaker.presentation.ui.parties.details.PartyDetailsFragment
import com.example.partymaker.presentation.ui.cocktails.search.CocktailSearchFragment
import com.example.partymaker.presentation.ui.meals.details.MealDetailsFragment
import com.example.partymaker.presentation.ui.meals.search.MealSearchFragment
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
    fun inject(mealSearchFragment: MealSearchFragment)
    fun inject(cocktailSearchFragment: CocktailSearchFragment)
    fun inject(mealDetailsFragment: MealDetailsFragment)
    fun inject(cocktailDetailsFragment: CocktailDetailsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): PresentationComponent
    }
}
