package com.example.partymaker.presentation.ui.parties.details

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.partymaker.databinding.ItemPartyBinding
import com.example.partymaker.domain.entities.Party

//class PartyDetailsRecyclerViewAdapter(
//    private val values: MutableList<Party>,
//    private val mListener: OnDeleteButtonClickListener
//) : RecyclerView.Adapter<PartyDetailsRecyclerViewAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        return ViewHolder(
//            ItemPartyBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = values[position]
//        holder.title.text = item.name
//        holder.btnEdit.setOnClickListener {
//            mListener.onDeleteButtonClick(item.id, item.name)
//        }
//    }
//
//    override fun getItemCount(): Int = values.size
//
//    inner class ViewHolder(binding: ItemPartyBinding) : RecyclerView.ViewHolder(binding.root) {
//        val title: TextView = binding.tvPartyListItemTitle
//        val btnEdit: Button = binding.btnPartyListItemEdit
//
//        override fun toString(): String {
//            return super.toString() + " '" + title.text + "'"
//        }
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(partyList: List<Party>) {
//        values.clear()
//        values.addAll(partyList)
//        notifyDataSetChanged()
//    }
//
//    interface OnDeleteButtonClickListener {
//        fun onDeleteButtonClick(itemId: Long, name: String)
//    }
//}
