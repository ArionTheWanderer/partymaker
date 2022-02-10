package com.example.partymaker.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.partymaker.presentation.ui.placeholder.PlaceholderContent.PlaceholderItem
import com.example.partymaker.databinding.ItemPartyBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PartiesRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<PartiesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPartyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemPartyBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvPartiesItemTitle

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

}