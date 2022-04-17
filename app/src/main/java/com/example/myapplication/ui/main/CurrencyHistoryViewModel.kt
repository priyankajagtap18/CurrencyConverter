package com.example.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.data.remote.model.CurrencyRates
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.repo.CurrencyConverterRepository
import com.example.myapplication.util.CurrencyExtension
import com.example.myapplication.util.getCalendarDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CurrencyHistoryViewModel @Inject constructor(

private val currencyConverterRepo: CurrencyConverterRepository,
) :  ViewModel(){

    val currencyCallback: LiveData<CurrencyCallback> = MutableLiveData()
    val currencyHistory = MutableLiveData<List<CurrencyRates>>(emptyList())

    private val customViewModelScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    init {
        fetchCurrencyHistory(getCalendarDate(CurrencyExtension.DATE_FORMAT_YYYY_MM_DD))
    }

    fun fetchCurrencyHistory(date : String) {
        (currencyCallback as MutableLiveData).value = CurrencyCallback.Loading
        customViewModelScope.launch() {
            when (val rateResponse = currencyConverterRepo.getHistoricalData(date)) {
                is ResultWrapper.Error -> currencyCallback.postValue(rateResponse.error?.let {
                    CurrencyCallback.Failure(it)
                })
                is ResultWrapper.Success -> {
                    currencyCallback.postValue(rateResponse.data?.let {
                        CurrencyCallback.Success(it)

                    })
                    withContext(Dispatchers.Main) {
                        val result = rateResponse.data?.rates
                        val rate : MutableList<CurrencyRates> = mutableListOf()
                        result?.forEach { (key, value) ->
                            rate.add(CurrencyRates(key, value))
                        }

                        currencyHistory.postValue(rate)
                        Log.i("spinnerdata", ""+rate.size)
                        //val arr = rateResponse.data?.let { CurrencyCallback.Success(it).success.rates.keys }
                        //  fromSpinnerArray.set(arr.toTypedArray())
                        //Log.i("spinnerdata", "spinnerdata" + fromSpinnerArray.value?.size)
                    }

                }
            }
        }
    }

}