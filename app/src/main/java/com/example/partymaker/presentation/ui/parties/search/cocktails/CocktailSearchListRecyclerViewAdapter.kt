package com.example.partymaker.presentation.ui.parties.search.cocktails

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.partymaker.databinding.ItemCocktailSearchBinding
import com.example.partymaker.domain.entities.CocktailDomain

class CocktailSearchListRecyclerViewAdapter(
    private val values: MutableList<CocktailDomain>
//    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<CocktailSearchListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemCocktailSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.name
        holder.alcoholic.text = item.alcoholic.name
//        holder.root.setOnClickListener {
//            mListener.onItemClick(itemId = item.id, partyName = item.name)
//        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemCocktailSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvCocktailSearchListItemTitle
        val alcoholic: TextView = binding.tvCocktailSearchListItemAlcoholic
        val root = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cocktailList: List<CocktailDomain>) {
        Log.d(TAG, "incoming list size: ${cocktailList.size}")
        Log.d(TAG, "setData before clearing: ${values.size}")
        values.clear()
        Log.d(TAG, "setData after clearing: ${values.size}")
        values.addAll(cocktailList)
        Log.d(TAG, "setData after setting new data: ${values.size}")
        notifyDataSetChanged()
        Log.d(TAG, "setData after notifying: ${values.size}")
    }

    interface OnItemClickListener {
        fun onItemClick(itemId: Long, partyName: String)
    }
}

private const val TAG = "CocktailSearchListRecyclerV"