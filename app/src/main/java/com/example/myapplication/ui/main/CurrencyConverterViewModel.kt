package com.example.myapplication.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.data.remote.model.ErrorWrapper
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.repo.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyConverterRepo: CurrencyConverterRepository,
) : ViewModel() {

    val allCurrencies: LiveData<CurrencyCallback> = MutableLiveData()
    val convertedCurrency: LiveData<CurrencyCallback> = MutableLiveData()
    var convertedToValue: ObservableField<String> = ObservableField<String>()
    var convertedFromValue: ObservableField<String> = ObservableField<String>()
    private val customViewModelScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    init {
        fetchAllCurrency()
    }

    private fun fetchAllCurrency() {
        (allCurrencies as MutableLiveData).value = CurrencyCallback.Loading
        customViewModelScope.launch() {
            when (val rateResponse = currencyConverterRepo.getAllCurrency()) {
                is ResultWrapper.Error -> allCurrencies.postValue(rateResponse.error?.let {
                    CurrencyCallback.Failure(it)
                })
                is ResultWrapper.Success -> {
                    allCurrencies.postValue(rateResponse.data?.let {
                        CurrencyCallback.Success(it)

                    })
                    withContext(Dispatchers.Main) {
                    }

                }
            }
        }
    }

    fun convert(fromAmount: String, toAmount: String, fromCurrency: String, toCurrency: String) {
        val fromDoubleAmount = fromAmount.toFloatOrNull()
        val toDoubleAmount = toAmount.toFloatOrNull()
        if ((fromDoubleAmount == 0f || fromDoubleAmount == null) && (toDoubleAmount == 0f || toDoubleAmount == null)) {
            (convertedCurrency as MutableLiveData).postValue(
                CurrencyCallback.Failure(
                    ErrorWrapper(
                        throwable = Exception()
                    )
                )
            )
            return
        }
        customViewModelScope.launch() {
            when (val rateResponse = currencyConverterRepo.getAllCurrency()) {
                is ResultWrapper.Error -> (convertedCurrency as MutableLiveData).postValue(
                    rateResponse.error?.let { CurrencyCallback.Failure(it) })
                is ResultWrapper.Success -> {
                    if (rateResponse.data?.rates?.containsKey(fromCurrency) == true && rateResponse.data?.rates?.containsKey(
                            toCurrency
                        ) == true
                    ) {
                        val computedValue: Float

                        if (toDoubleAmount == 0f || toDoubleAmount == null) {
                            computedValue =
                                (rateResponse.data.rates.getValue(toCurrency)) * fromDoubleAmount!!
                            convertedToValue?.set(String.format("%.2f", computedValue))
                        } else {
                            computedValue =
                                (rateResponse.data.rates.getValue(fromCurrency)) * toDoubleAmount!!
                            convertedFromValue?.set(String.format("%.2f", computedValue))
                        }
                    }

                    (convertedCurrency as MutableLiveData).postValue(rateResponse.data?.let {
                        CurrencyCallback.Success(
                            it
                        )
                    })
                }
            }
        }
    }

}