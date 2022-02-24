package com.example.partymaker.presentation.ui.cocktails

import androidx.recyclerview.widget.DiffUtil
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.entities.MealDomain

class CocktailDiffUtilCallback(
    private val oldList: List<CocktailDomain>,
    private val newList: List<CocktailDomain>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].cocktailId == newList[newItemPosition].cocktailId


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].alcoholic == newList[newItemPosition].alcoholic
                && oldList[oldItemPosition].thumbnailLink == newList[newItemPosition].thumbnailLink)
    }
}
