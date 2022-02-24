package com.example.partymaker.presentation.ui.parties.details.pager.meals

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.example.partymaker.databinding.ItemMealListBinding
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.presentation.ui.meals.MealDiffUtilCallback

class MealListRecyclerViewAdapter(
    private val values: MutableList<MealDomain>,
    private val mListener: OnItemClickListener,
    private val glide: RequestManager
) : RecyclerView.Adapter<MealListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemMealListBinding.inflate(
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
            .into(holder.image)
        holder.btnDelete.setOnClickListener {
            mListener.onDeleteButtonClick(item.mealId)
        }
        holder.root.setOnClickListener {
            mListener.onItemClick(mealId = item.mealId, mealName = item.name)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemMealListBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvMealListItemTitle
        val category: TextView = binding.tvMealListItemCategory
        val image: ImageView = binding.ivItemMeal
        val btnDelete: ImageView = binding.ivMealListItemDelete
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
        fun onDeleteButtonClick(mealId: Long)
    }
}
