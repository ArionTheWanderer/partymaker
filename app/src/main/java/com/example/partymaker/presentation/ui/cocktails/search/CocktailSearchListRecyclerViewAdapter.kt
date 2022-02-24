package com.example.partymaker.presentation.ui.cocktails.search

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.partymaker.databinding.ItemCocktailSearchBinding
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.presentation.ui.cocktails.CocktailDiffUtilCallback

class CocktailSearchListRecyclerViewAdapter(
    private val values: MutableList<CocktailDomain>,
    private val mListener: OnItemClickListener,
    private val glide: RequestManager
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
        holder.alcoholic.text = CocktailAlcoholicEnum.enumToString(item.alcoholic)
        glide
            .load(item.thumbnailLink)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.image)
        holder.root.setOnClickListener {
            mListener.onItemClick(cocktailId = item.cocktailId, cocktailName = item.name)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemCocktailSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvCocktailSearchListItemTitle
        val alcoholic: TextView = binding.tvCocktailSearchListItemAlcoholic
        val image: ImageView = binding.ivItemCocktailSearch
        val root = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

    fun setData(cocktailList: List<CocktailDomain>) {
        val diffUtilCallback = CocktailDiffUtilCallback(values, cocktailList)
        val diffUtil = DiffUtil.calculateDiff(diffUtilCallback)
        values.clear()
        values.addAll(cocktailList)
        diffUtil.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(cocktailId: Long, cocktailName: String)
    }
}

private const val TAG = "CocktailSearchListRecyclerV"