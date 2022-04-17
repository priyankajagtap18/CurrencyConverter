package com.example.myapplication.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.model.CurrencyRates
import com.example.myapplication.ui.main.CurrencyHistoryAdapter

@BindingAdapter("items")
fun bindItemViewModels(
    recyclerView: RecyclerView,
    itemViewModels: List<CurrencyRates>?
) {
    val adapter = getAdapter(recyclerView)
    adapter.updateItems(itemViewModels)
}

private fun getAdapter(recyclerView: RecyclerView): CurrencyHistoryAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is CurrencyHistoryAdapter) {
        recyclerView.adapter as CurrencyHistoryAdapter
    } else {
        val historyAdapter = CurrencyHistoryAdapter()
        recyclerView.adapter = historyAdapter
        historyAdapter
    }
}