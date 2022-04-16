package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.databinding.FragmentCurrencyConverterBinding
import com.example.myapplication.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrencyConvertorFragment : BaseFragment() {

    private lateinit var binding: FragmentCurrencyConverterBinding

    // Initializing the viewModel on call using KTX-Fragments extension.
    private val viewModel: CurrencyConverterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_currency_converter,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        observeLiveDataEvents()
    }

    private fun observeLiveDataEvents() {
        viewModel.allCurrencies.observe(viewLifecycleOwner) { currencyResponse ->
            when (currencyResponse) {
                is CurrencyCallback.Success -> {
                    if (!currencyResponse.success.success) {
                        showError(currencyResponse.success.baseError.info)
                    } else {
                        currencyResponse.success?.rates?.keys?.let { it -> setAdapter(it.toTypedArray()) }
                    }
                    binding.progressBar.isVisible = false

                }
                is CurrencyCallback.Failure -> {
                    showError(resources.getString(R.string.app_error))
                    binding.progressBar.isVisible = false
                }
                is CurrencyCallback.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }

        viewModel.convertedCurrency.observe(viewLifecycleOwner) { currencyResponse ->
            if (currencyResponse is CurrencyCallback.Failure) {
                showError(resources.getString(R.string.app_error))
            }
        }
    }

    private fun initListener() {
        binding.btnConvert.setOnClickListener {
            binding.spFromCurrency?.selectedItem?.toString()?.let { it1 ->
                binding.spToCurrency.selectedItem?.toString()?.let { it2 ->
                    viewModel.convert(
                        binding.etFrom.text.toString(),
                        binding.etTo.text.toString(),
                        it1,
                        it2,
                    )
                }
            }
        }
        binding.btnSwap.setOnClickListener {
            val fromAmount = viewModel.convertedFromValue.get()
            val toAmount = viewModel.convertedToValue.get()
            binding.etFrom.setText(toAmount.toString())
            binding.etTo.setText(fromAmount.toString())
            val fromSpinnerPosition = binding.spFromCurrency.selectedItemPosition
            val toSpinnerPosition = binding.spToCurrency.selectedItemPosition
            binding.spFromCurrency.setSelection(toSpinnerPosition)
            binding.spToCurrency.setSelection(fromSpinnerPosition)
            val arrayAdapter = binding.spFromCurrency.adapter as ArrayAdapter<*>
            arrayAdapter.notifyDataSetChanged()
            val arrayAdapter2 = binding.spToCurrency.adapter as ArrayAdapter<*>
            arrayAdapter2.notifyDataSetChanged()
        }
    }

    private fun setAdapter(managerArr: Array<String>) {
        val arrayFromAdapter = ArrayAdapter(
            activity?.applicationContext!!,
            android.R.layout.simple_spinner_item,
            managerArr
        )
        binding.spFromCurrency.adapter = arrayFromAdapter
        val arrayToAdapter = ArrayAdapter(
            activity?.applicationContext!!,
            android.R.layout.simple_spinner_item,
            managerArr
        )
        binding.spToCurrency.adapter = arrayToAdapter
    }
}