package com.example.cloudwebservice5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudwebservice5.Data.MessageData
import com.example.cloudwebservice5.databinding.ItemMessageBinding

class MessageAdapter(val items: List<MessageData>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: MessageData, pos: Int)
    }

    var itemClickListener: OnItemClickListener ?= null

    inner class ViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.messageBtn.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val view = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.binding.nameTextView.text = items[position].name
        holder.binding.titleTextView.text = items[position].title
    }

    override fun getItemCount(): Int {
        return items.size
    }


}