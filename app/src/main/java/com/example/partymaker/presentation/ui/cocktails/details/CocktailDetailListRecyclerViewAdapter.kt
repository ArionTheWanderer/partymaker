package com.example.partymaker.presentation.ui.cocktails.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.partymaker.databinding.ItemIngredientDetailBinding

class CocktailDetailListRecyclerViewAdapter(
    private val values: MutableList<Pair<String, String>>
) : RecyclerView.Adapter<CocktailDetailListRecyclerViewAdapter.ViewHolder>() {

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
        val item = values[position]
        holder.title.text = item.first
        holder.content.text = item.second
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemIngredientDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvItemIngDetTitle
        val content: TextView = binding.tvItemIngDetContent

        override fun toString(): String {
            return super.toString() + " '" + title.text + " " + content.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Pair<String, String>>) {
        Log.d(TAG, "incoming list size: ${newList.size}")
        Log.d(TAG, "setData before clearing: ${values.size}")
        values.clear()
        Log.d(TAG, "setData after clearing: ${values.size}")
        values.addAll(newList)
        Log.d(TAG, "setData after setting new data: ${values.size}")
        notifyDataSetChanged()
        Log.d(TAG, "setData after notifying: ${values.size}")
    }
}

private const val TAG = "CocktailDetailListRecyclerV"
