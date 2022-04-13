package com.example.myapplication.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.remote.service.IDispatcherProvider
import com.example.myapplication.data.repo.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyConverterRepo: CurrencyConverterRepository,
    private val dispatchers: IDispatcherProvider
) : ViewModel() {

    @Bindable
    val latestRates: MutableLiveData<CurrencyResponse> = MutableLiveData()
    val latestRatesWithDate: MutableLiveData<CurrencyResponse> = MutableLiveData()


    fun fetchAllCurrency() {
        getNavigator().showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            when (val result: ResultWrapper<CurrencyResponse> =
                withContext(Dispatchers.IO) { dataManager.getCurrenciesWithDetail() }) {
                is ResultWrapper.Success -> {
                    getNavigator().hideLoading()
                    getNavigator().currencyDetailSuccess(result.data.response)
                }
                is ResultWrapper.Error -> {
                    getNavigator().hideLoading()
                    getNavigator().onError(result.exception.message)
                }
            }
        }
    }
}