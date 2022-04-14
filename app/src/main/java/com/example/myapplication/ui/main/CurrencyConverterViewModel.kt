package com.example.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.remote.service.IDispatcherProvider
import com.example.myapplication.data.repo.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyConverterRepo: CurrencyConverterRepository,
    private val dispatchers: IDispatcherProvider
) : ViewModel() {

   // @Bindable
    val allSymbols: MutableLiveData<CurrencyResponse> = MutableLiveData()
    val spinnerSymbolList: MutableList<String> = mutableListOf()
    var managerArr: Array<String>? = null
//    val latestRatesWithDate: MutableLiveData<CurrencyResponse> = MutableLiveData()


    fun fetchAllCurrency() {

//        GlobalScope.launch(Dispatchers.Main) {
//            when (val result: ResultWrapper<CurrencyResponse> =
//                withContext(Dispatchers.IO) { dataManager.getCurrenciesWithDetail() }) {
//                is ResultWrapper.Success -> {
//
//                    getNavigator().currencyDetailSuccess(result.data.response)
//                }
//                is ResultWrapper.Error -> {
//
//                    getNavigator().onError(result.exception.message)
//                }
//            }
//        }

        viewModelScope.launch(dispatchers.io) {
            when (val rateResponse = currencyConverterRepo.getAllCurrency()) {
                is ResultWrapper.Error -> allSymbols.postValue(rateResponse.data)
                is ResultWrapper.Success -> {
                    Log.i("rates", "" + rateResponse.data?.timestamp)
//                    spinnerSymbolList.addAll(rateResponse.data?.rates?.keys?.toTypedArray())
                    managerArr = rateResponse.data?.rates?.keys?.toTypedArray()
                    allSymbols.postValue(rateResponse.data)

                }
            }
        }
    }
}