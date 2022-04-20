package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.CurrencyHistoryChild
import com.example.myapplication.databinding.RowCurrencyHistoryDetailBinding

/**
 * CurrencyHistoryChildAdapter - Child history adapter contains horizontal list of currency and rate for each day from calendar
 */
class CurrencyHistoryChildAdapter(private var historicalList: List<CurrencyHistoryChild>) : RecyclerView.Adapter<CurrencyHistoryChildAdapter.BindableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding : ViewDataBinding =  DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_currency_history_detail,
            parent,
            false
        )

        return BindableViewHolder(binding)
    }

    override fun getItemCount(): Int = historicalList.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bindData(historicalList.get(position) )
    }

    fun updateItems(items: List<CurrencyHistoryChild>?) {
        historicalList = items ?: emptyList()
        notifyDataSetChanged()
    }


open inner class BindableViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(list: CurrencyHistoryChild) {
        with(binding as RowCurrencyHistoryDetailBinding) {
            viewModel = list
        }
    }
}
}