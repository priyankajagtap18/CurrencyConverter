package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.databinding.FragmentCurrencyHistoryBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.util.CurrencyExtension
import com.example.myapplication.util.getCalendarDate
import dagger.hilt.android.AndroidEntryPoint


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
        viewModel.fetchCurrencyHistory(getCalendarDate(CurrencyExtension.DATE_FORMAT_YYYY_MM_DD))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        observeLiveDataEvents()
    }

    private fun observeLiveDataEvents() {
        viewModel.currencyCallback.observe(this) {
            when (it) {
                is CurrencyCallback.Success -> {
                    showSuccess("data" + it.success.rates.size)
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
    }


    private fun initListener() {


    }


}