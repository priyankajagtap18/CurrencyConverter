package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.data.remote.model.CurrencyHistoryChild
import com.example.myapplication.databinding.FragmentCurrencyHistoryBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.util.ChartHelper
import dagger.hilt.android.AndroidEntryPoint


/**
 * CurrencyHistoryFragment -
 * 1. Shows history of past three days from calendar in list format. Child list of each day is horizontal scrollable.
 * 2. Display data of first day in line chart format - Used MPChart library
 *
 */
@AndroidEntryPoint
class CurrencyHistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentCurrencyHistoryBinding

    // Initializing the viewModel on call using KTX-Fragments extension.
    private val viewModel: CurrencyHistoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_currency_history,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveDataEvents()
    }

    private fun observeLiveDataEvents() {
        viewModel.currencyCallback.observe(this) {
            when (it) {
                is CurrencyCallback.Success -> {
                    if (!it.success.success) {
                        showError(it.success.baseError.info)
                    }
                }
                is CurrencyCallback.Failure -> {
                    showError(it.errorText.message)
                }
                is CurrencyCallback.Loading -> {

                }
            }
        }

        viewModel.currencyHistory.observe(this) {
            it?.let {
                if (!it.isNullOrEmpty())
                    initLineChart(it.get(0).rates)
            }
        }
    }


    private fun initLineChart(rates: List<CurrencyHistoryChild>) {
        ChartHelper(binding.lineChartHistory, rates)
    }

}