package com.example.partymaker.presentation.ui.parties.details.pager.cocktails

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.example.partymaker.databinding.ItemCocktailListBinding
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain

class CocktailListRecyclerViewAdapter(
    private val values: MutableList<CocktailDomain>,
    private val mListener: OnItemClickListener,
    private val glide: RequestManager
) : RecyclerView.Adapter<CocktailListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemCocktailListBinding.inflate(
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
        holder.btnDelete.setOnClickListener {
            mListener.onDeleteButtonClick(item.cocktailId)
        }
        glide
            .load(item.thumbnailLink)
            .into(holder.image)
        holder.root.setOnClickListener {
            mListener.onItemClick(cocktailId = item.cocktailId, cocktailName = item.name)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemCocktailListBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvCocktailListItemTitle
        val alcoholic: TextView = binding.tvCocktailListItemAlcoholic
        val image: ImageView = binding.ivItemCocktail
        val btnDelete: ImageView = binding.ivCocktailListItemDelete
        val root = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cocktailList: List<CocktailDomain>) {
        values.clear()
        values.addAll(cocktailList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(cocktailId: Long, cocktailName: String)
        fun onDeleteButtonClick(cocktailId: Long)
    }
}
