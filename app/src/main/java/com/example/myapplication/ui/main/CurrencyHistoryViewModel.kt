package com.example.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.repo.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CurrencyHistoryViewModel @Inject constructor(

private val currencyConverterRepo: CurrencyConverterRepository,
) :  ViewModel(){

    val currencyHistory: LiveData<CurrencyCallback> = MutableLiveData()
    private val customViewModelScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    init {
    }

    fun fetchCurrencyHistory(date : String) {
        (currencyHistory as MutableLiveData).value = CurrencyCallback.Loading
        customViewModelScope.launch() {
            when (val rateResponse = currencyConverterRepo.getHistoricalData(date)) {
                is ResultWrapper.Error -> currencyHistory.postValue(rateResponse.error?.let {
                    CurrencyCallback.Failure(it)
                })
                is ResultWrapper.Success -> {
                    currencyHistory.postValue(rateResponse.data?.let {
                        CurrencyCallback.Success(it)

                    })
                    withContext(Dispatchers.Main) {
                        Log.i("spinnerdata", "spinnerdata")
                        //val arr = rateResponse.data?.let { CurrencyCallback.Success(it).success.rates.keys }
                        //  fromSpinnerArray.set(arr.toTypedArray())
                        //Log.i("spinnerdata", "spinnerdata" + fromSpinnerArray.value?.size)
                    }

                }
            }
        }
    }

}