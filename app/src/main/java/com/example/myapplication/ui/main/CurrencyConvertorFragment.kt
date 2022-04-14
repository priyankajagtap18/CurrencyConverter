package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCurrencyConverterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrencyConvertorFragment : Fragment(R.layout.fragment_currency_converter) {

    private lateinit var binding: FragmentCurrencyConverterBinding

    // Initializing the viewModel on call using KTX-Fragments extension.
    private val viewModel: CurrencyConverterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrencyConverterBinding.bind(view)
        binding.viewModel = viewModel
        viewModel.fetchAllCurrency()
        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.etTo.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString(),
            )
            Toast.makeText(context, "message", Toast.LENGTH_SHORT).show()
        }

        viewModel.allCurrencies.observe(viewLifecycleOwner, Observer { currencyResponse ->
            setAdapter(currencyResponse.rates.keys.toTypedArray())
        })




    }

    fun setAdapter(managerArr: Array<String>) {
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