package com.example.myapplication.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.CurrencyCallback
import com.example.myapplication.data.remote.model.CurrencyHistoryChild
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
    var otherCurrency: LiveData<List<CurrencyHistoryChild>> = MutableLiveData()
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
                }
            }
        }
    }

    fun convert(fromAmount: String, toAmount: String, fromCurrency: String, toCurrency: String) {
        val fromFloatAmount = fromAmount.toFloatOrNull()
        val toFloatAmount = toAmount.toFloatOrNull()
        if ((fromFloatAmount == 0f || fromFloatAmount == null) && (toFloatAmount == 0f || toFloatAmount == null)) {
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

                        if (toFloatAmount == 0f || toFloatAmount == null) {
                            computedValue =
                                (rateResponse.data.rates.getValue(toCurrency)) * fromFloatAmount!!
                            convertedToValue?.set(String.format("%.2f", computedValue))
                            createOtherCurrencyData(
                                rateResponse.data.rates,
                                fromCurrency,
                                toCurrency,
                                fromFloatAmount,
                                toFloatAmount,
                                false
                            )
                        } else {
                            computedValue =
                                (rateResponse.data.rates.getValue(fromCurrency)) * toFloatAmount!!
                            convertedFromValue?.set(String.format("%.2f", computedValue))
                            createOtherCurrencyData(
                                rateResponse.data.rates,
                                fromCurrency,
                                toCurrency,
                                fromFloatAmount,
                                toFloatAmount,
                                true
                            )
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

    private suspend fun createOtherCurrencyData(
        rates: LinkedHashMap<String, Float>,
        fromCurrency: String,
        toCurrency: String,
        fromFloatAmount: Float?,
        toFloatAmount: Float?,
        fromValue: Boolean
    ) {
        val otherCurrencyList: MutableList<CurrencyHistoryChild> = mutableListOf()
        val perRate = "1 "
        val equation = " = "
        val space = " "
        rates.forEach { (key, value) ->
            val rateValue: Float
            val detailValue: String
            val strBuilder: StringBuilder = StringBuilder()
            if (fromValue) {
                rateValue =
                    (String.format("%.2f", ((rates.getValue(key)) * toFloatAmount!!))).toFloat()
                detailValue = strBuilder.append(perRate).append(toCurrency).append(equation)
                    .append((String.format("%.2f", value))).append(space).append(key).toString()
            } else {
                rateValue =
                    (String.format("%.2f", ((rates.getValue(key)) * fromFloatAmount!!))).toFloat()
                detailValue = strBuilder.append(perRate).append(fromCurrency).append(equation)
                    .append((String.format("%.2f", value))).append(space).append(key).toString()
            }
            otherCurrencyList.add(CurrencyHistoryChild(key, rateValue, detailValue))
        }
        withContext(Dispatchers.Main) {
            (otherCurrency as MutableLiveData).value = otherCurrencyList
        }
    }

}