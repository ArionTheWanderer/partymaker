package com.example.partymaker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.partymaker.presentation.di.party.PartyScope
import javax.inject.Inject
import javax.inject.Provider

class PartyViewModelFactory @Inject constructor(
    private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val result = viewModelMap[modelClass] ?: viewModelMap.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return result.get() as T
    }
}
