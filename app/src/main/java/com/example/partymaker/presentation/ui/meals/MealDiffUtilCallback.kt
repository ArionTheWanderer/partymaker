package com.example.partymaker.presentation.ui.meals

import androidx.recyclerview.widget.DiffUtil
import com.example.partymaker.domain.entities.MealDomain

class MealDiffUtilCallback(
    private val oldList: List<MealDomain>,
    private val newList: List<MealDomain>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].mealId == newList[newItemPosition].mealId


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].category == newList[newItemPosition].category
                && oldList[oldItemPosition].thumbnailLink == newList[newItemPosition].thumbnailLink)
    }
}
