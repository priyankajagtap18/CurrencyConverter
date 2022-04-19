package com.example.myapplication.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.model.CurrencyHistoryChild
import com.example.myapplication.data.remote.model.CurrencyHistoryParent
import com.example.myapplication.ui.main.CurrencyHistoryAdapter
import com.example.myapplication.ui.main.OtherCurrencyAdapter

@BindingAdapter("items")
fun bindParentData(
    recyclerView: RecyclerView,
    itemViewModels: List<CurrencyHistoryParent>?
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

@BindingAdapter("otherCurrencyItems")
fun bindOtherCurrencyData(
    recyclerView: RecyclerView,
    itemViewModels: List<CurrencyHistoryChild>?
) {
    val adapter = getOtherCurrencyAdapter(recyclerView)
    adapter.updateItems(itemViewModels)
}

private fun getOtherCurrencyAdapter(recyclerView: RecyclerView): OtherCurrencyAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is OtherCurrencyAdapter) {
        recyclerView.adapter as OtherCurrencyAdapter
    } else {
        val historyAdapter = OtherCurrencyAdapter()
        recyclerView.adapter = historyAdapter
        historyAdapter
    }
}
//
//@BindingAdapter("subItems")
//fun bindChildData(
//    recyclerView: RecyclerView,
//    itemViewModels: List<CurrencyHistoryChild>?
//) {
//    val adapter = getChildAdapter(recyclerView)
//    adapter.updateItems(itemViewModels)
//}
//
//private fun getChildAdapter(recyclerView: RecyclerView): CurrencyHistoryChildAdapter {
//    return if (recyclerView.adapter != null && recyclerView.adapter is CurrencyHistoryChildAdapter) {
//        recyclerView.adapter as CurrencyHistoryChildAdapter
//    } else {
//        val historyAdapter = CurrencyHistoryChildAdapter()
//        recyclerView.adapter = historyAdapter
//        historyAdapter
//    }
//}

