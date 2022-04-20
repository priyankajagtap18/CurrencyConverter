package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.CurrencyHistoryParent
import com.example.myapplication.databinding.RowCurrencyHistoryParentBinding

/**
 * CurrencyHistoryAdapter - Parent history adapter with Date as title and sublist of currency and rate is displayed
 */
class CurrencyHistoryAdapter : RecyclerView.Adapter<CurrencyHistoryAdapter.BindableViewHolder>() {

    lateinit var historicalList: List<CurrencyHistoryParent>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_currency_history_parent,
            parent,
            false
        )

        return BindableViewHolder(binding)
    }

    override fun getItemCount(): Int = historicalList.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bindData(historicalList[position], position)
    }

    fun updateItems(items: List<CurrencyHistoryParent>?) {
        historicalList = items ?: emptyList()
        notifyDataSetChanged()
    }


    open inner class BindableViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(list: CurrencyHistoryParent, position: Int) {
            with(binding as RowCurrencyHistoryParentBinding) {
                viewModel = list
                binding.rvCurrencyHistoryChild.adapter =
                    CurrencyHistoryChildAdapter(historicalList[position].rates)
                binding.executePendingBindings()
            }
        }
    }
}