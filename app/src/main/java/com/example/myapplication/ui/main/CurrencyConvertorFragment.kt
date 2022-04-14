package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCurrencyConverterBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
            Toast.makeText(context, "message", Toast.LENGTH_SHORT).show()
        }

        viewModel.allSymbols.observe(viewLifecycleOwner, Observer { currencyResponse ->
//            val retMap: HashMap<String, Double> = Gson().fromJson(
//                currencyResponse.rates, object : TypeToken<HashMap<String?, Any?>?>() {}.type
//            )

            Toast.makeText(context, ""+arrayOf(currencyResponse.rates.keys.elementAt(0)), Toast.LENGTH_SHORT).show()
        })
    }
}