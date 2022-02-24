package com.example.partymaker.presentation.ui.meals.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.partymaker.databinding.ItemMealSearchBinding
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.presentation.ui.meals.MealDiffUtilCallback

class MealSearchListRecyclerViewAdapter(
    private val values: MutableList<MealDomain>,
    private val mListener: OnItemClickListener,
    private val glide: RequestManager
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
        holder.category.text = item.category.name
        glide
            .load(item.thumbnailLink)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.image)
        holder.root.setOnClickListener {
            mListener.onItemClick(mealId = item.mealId, mealName = item.name)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemMealSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvMealSearchListItemTitle
        val category: TextView = binding.tvMealSearchListItemCategory
        val image: ImageView = binding.ivItemMealSearch
        val root = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

    fun setData(mealList: List<MealDomain>) {
        val diffUtilCallback = MealDiffUtilCallback(values, mealList)
        val diffUtil = DiffUtil.calculateDiff(diffUtilCallback)
        values.clear()
        values.addAll(mealList)
        diffUtil.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(mealId: Long, mealName: String)
    }
}

private const val TAG = "MealSearchListRecyclerV"
