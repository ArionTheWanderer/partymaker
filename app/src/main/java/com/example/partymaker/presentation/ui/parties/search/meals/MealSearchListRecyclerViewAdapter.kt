package com.example.partymaker.presentation.ui.parties.search.meals

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.partymaker.databinding.ItemMealSearchBinding
import com.example.partymaker.domain.entities.MealDomain

class MealSearchListRecyclerViewAdapter(
    private val values: MutableList<MealDomain>
//    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<MealSearchListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemMealSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.name
//        holder.root.setOnClickListener {
//            mListener.onItemClick(itemId = item.id, partyName = item.name)
//        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemMealSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvMealSearchListItemTitle
        val root = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(mealList: List<MealDomain>) {
        Log.d(TAG, "incoming list size: ${mealList.size}")
        Log.d(TAG, "setData before clearing: ${values.size}")
        values.clear()
        Log.d(TAG, "setData after clearing: ${values.size}")
        values.addAll(mealList)
        Log.d(TAG, "setData after setting new data: ${values.size}")
        notifyDataSetChanged()
        Log.d(TAG, "setData after notifying: ${values.size}")
    }

    interface OnItemClickListener {
        fun onItemClick(itemId: Long, partyName: String)
        fun onDeleteButtonClick(itemId: Long, partyName: String)
    }
}

private const val TAG = "MealSearchListRecyclerV"