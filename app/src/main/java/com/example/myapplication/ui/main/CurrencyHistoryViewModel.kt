package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repo.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyHistoryViewModel @Inject constructor(

private val currencyConverterRepo: CurrencyConverterRepository,
) :  ViewModel(){

}