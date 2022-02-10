package com.example.partymaker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.presentation.ui.model.PartyUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PartyDialogViewModel : ViewModel() {
    private val _parties = MutableLiveData<DataState<PartyUi>>()

    val parties: LiveData<DataState<PartyUi>>
        get() = _parties

    fun addData(id: Long, partyName: String) {
        val party = Party(id, partyName)

        CoroutineScope(Dispatchers.Main.immediate).launch {
            var actualId = id

            if (id > 0) {
                update(donut)
            } else {
                actualId = insert(donut)
            }
        }
    }
}
