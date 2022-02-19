package com.example.partymaker.presentation.ui.parties

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.partymaker.databinding.ItemPartyBinding
import com.example.partymaker.domain.entities.Party

class PartyListRecyclerViewAdapter(
    private val values: MutableList<Party>,
    private val mListener: OnItemClickListener
) : RecyclerView.Adapter<PartyListRecyclerViewAdapter.ViewHolder>() {

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
        holder.title.text = item.name
        holder.btnDelete.setOnClickListener {
            mListener.onDeleteButtonClick(item.id, item.name)
        }
        holder.root.setOnClickListener {
            mListener.onItemClick(itemId = item.id, partyName = item.name)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemPartyBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.tvPartyListItemTitle
        val btnDelete: ImageView = binding.ivPartyListItemDelete
        val root = binding.root

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(partyList: List<Party>) {
        values.clear()
        values.addAll(partyList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(itemId: Long, partyName: String)
        fun onDeleteButtonClick(itemId: Long, partyName: String)
    }
}
