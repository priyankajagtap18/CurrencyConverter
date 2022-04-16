package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RowCurrencyHistoryDetailBinding

class CurrencyHistoryAdapter(private val items: HashMap<String, Double>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowCurrencyHistoryDetailBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(val binding: RowCurrencyHistoryDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RowCurrencyHistoryDetailBinding) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}