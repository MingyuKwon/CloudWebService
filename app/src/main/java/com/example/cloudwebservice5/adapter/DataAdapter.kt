package com.example.cloudwebservice5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudwebservice5.Data.Data
import com.example.cloudwebservice5.databinding.RowDataBinding

class DataAdapter(val items: List<String>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: String, pos: Int)
    }

    var itemClickListener: OnItemClickListener ?= null

    inner class ViewHolder(val binding: RowDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.downloadBtn.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        val view = RowDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        holder.binding.nameTextView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }


}