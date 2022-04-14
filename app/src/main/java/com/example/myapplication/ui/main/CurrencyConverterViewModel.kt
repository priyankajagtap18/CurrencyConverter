package com.example.myapplication.ui.main

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.CurrencyConvertResponse
import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.remote.service.IDispatcherProvider
import com.example.myapplication.data.repo.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyConverterRepo: CurrencyConverterRepository,
    private val dispatchers: IDispatcherProvider
) : ViewModel() {

    val allCurrencies: MutableLiveData<CurrencyResponse> = MutableLiveData()
    val spinnerSymbolList: MutableList<String> = mutableListOf()
    var position : ObservableInt = ObservableInt()
    val convertedCurrency: MutableLiveData<CurrencyResponse> = MutableLiveData()
    var convertedToValue : ObservableFloat = ObservableFloat()
    var convertedFromValue : ObservableFloat = ObservableFloat()
    var spinnerFromPosition : ObservableInt = ObservableInt()
    var spinnerToPosition : ObservableInt = ObservableInt()

    init {
        spinnerFromPosition.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                handleNewSelection()
            }
        })
    }

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
                is ResultWrapper.Error -> allCurrencies.postValue(rateResponse.data)
                is ResultWrapper.Success -> {
                    Log.i("rates", "" + rateResponse.data?.timestamp)
//                    spinnerSymbolList.addAll(rateResponse.data?.rates?.keys?.toTypedArray())
                    //managerArr = rateResponse.data?.rates?.keys?.toTypedArray()

                    allCurrencies.postValue(rateResponse.data)

                }
            }
        }
    }

        fun convert(fromAmount: String, toAmount: String, fromCurrency: String, toCurrency: String) {
            val fromDoubleAmount = fromAmount.toFloatOrNull()
            val toDoubleAmount = toAmount.toFloatOrNull()
//            if (fromDoubleAmount == null) {
//               // convertedCurrency.value = ResultWrapper.Error<CurrencyConvertResponse>
//                return
//            }

            viewModelScope.launch(dispatchers.io) {
                when(val rateResponse = currencyConverterRepo.getAllCurrency()) {
                    is ResultWrapper.Error -> convertedCurrency.postValue(rateResponse.data)
                    is ResultWrapper.Success -> {
                        if(rateResponse.data?.rates?.containsKey(fromCurrency) == true && rateResponse.data?.rates?.containsKey(toCurrency) == true) {
                            var computedValue : Float

                            if(toDoubleAmount == 0f || toDoubleAmount == null) {
                                computedValue =
                                    rateResponse.data.rates.getValue(toCurrency) * fromDoubleAmount!!
                                convertedToValue?.set(computedValue)
                            } else {
                                computedValue =
                                    (rateResponse.data.rates.getValue(fromCurrency) * 100)/ toDoubleAmount!!
                                convertedFromValue?.set(computedValue)
                            }


                        }



                            //convertedToValue?.set(it)
                        convertedCurrency.postValue(rateResponse.data)
                    }
                }
            }
        }

    private fun handleNewSelection() {
       // val oldSelection = convertedCurrency.value?.rates?.entries?.indexOfFirst { it. }
      //  if (spinnerFromPosition.get() != oldSelection) {
         //   convertedCurrency.value?.rates[spinnerFromPosition.get()].onItemSelected()
        }


}