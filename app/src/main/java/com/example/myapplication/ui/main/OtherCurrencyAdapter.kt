package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.CurrencyHistoryChild
import com.example.myapplication.databinding.RowOtherCurrencyBinding

/**
 * OtherCurrencyAdapter - Other Currency conversion adapter with list of currency, conversion of custom rate from/to field and per rate value is displayed
 */
class OtherCurrencyAdapter : RecyclerView.Adapter<OtherCurrencyAdapter.BindableViewHolder>() {

    lateinit var otherCurrencyList: List<CurrencyHistoryChild>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_other_currency,
            parent,
            false
        )

        return BindableViewHolder(binding)
    }

    override fun getItemCount(): Int = otherCurrencyList.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bindData(otherCurrencyList.get(position))
    }

    fun updateItems(items: List<CurrencyHistoryChild>?) {
        otherCurrencyList = items ?: emptyList()
        notifyDataSetChanged()
    }


    open inner class BindableViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(list: CurrencyHistoryChild) {
            with(binding as RowOtherCurrencyBinding) {
                viewModel = list
            }
        }
    }
}