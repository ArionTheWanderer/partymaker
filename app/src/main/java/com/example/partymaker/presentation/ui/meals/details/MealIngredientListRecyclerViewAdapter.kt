package com.example.partymaker.presentation.ui.meals.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.partymaker.databinding.ItemIngredientDetailBinding

class MealIngredientListRecyclerViewAdapter(
    private val ingredientsWithMeasures: MutableList<Pair<String, String>>
) : RecyclerView.Adapter<MealIngredientListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemIngredientDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ingredientsWithMeasures[position]
        holder.ingredient.text = item.first
        holder.measure.text = item.second
    }

    override fun getItemCount(): Int = ingredientsWithMeasures.size

    inner class ViewHolder(binding: ItemIngredientDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val ingredient: TextView = binding.tvItemIngDetTitle
        val measure: TextView = binding.tvItemIngDetContent

        override fun toString(): String {
            return super.toString() + " '" + ingredient.text + " " + measure.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Pair<String, String>>) {
        Log.d(TAG, "incoming list size: ${newList.size}")
        Log.d(TAG, "setData before clearing: ${ingredientsWithMeasures.size}")
        ingredientsWithMeasures.clear()
        Log.d(TAG, "setData after clearing: ${ingredientsWithMeasures.size}")
        ingredientsWithMeasures.addAll(newList)
        Log.d(TAG, "setData after setting new data: ${ingredientsWithMeasures.size}")
        notifyDataSetChanged()
        Log.d(TAG, "setData after notifying: ${ingredientsWithMeasures.size}")
    }
}

private const val TAG = "MealIngredientListRecyclerV"
