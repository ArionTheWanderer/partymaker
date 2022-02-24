package com.example.partymaker.presentation.ui.parties

import androidx.recyclerview.widget.DiffUtil
import com.example.partymaker.domain.entities.PartyDomain

class PartyDiffUtilCallback(
    private val oldList: List<PartyDomain>,
    private val newList: List<PartyDomain>
): DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name
}
