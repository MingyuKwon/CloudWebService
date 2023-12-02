package com.example.cloudwebservice5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudwebservice5.Data.CeoData
import com.example.cloudwebservice5.databinding.ItemCeoBinding

class ChatAdapter(val items: List<CeoData>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: CeoData, pos: Int)
    }

    var itemClickListener: OnItemClickListener ?= null

    inner class ViewHolder(val binding: ItemCeoBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.storeBtn.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val view = ItemCeoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        holder.binding.nameTextView.text = items[position].name
        holder.binding.phoneNumberTextView.text = items[position].phone_number
        holder.binding.careerTextView.text = items[position].career.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }


}