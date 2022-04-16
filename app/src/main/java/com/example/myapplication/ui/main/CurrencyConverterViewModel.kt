package com.example.myapplication.ui.main

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
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
    val spinnerSymbolList: MutableList<String> = mutableListOf()
    var position: ObservableInt = ObservableInt()
    val convertedCurrency: LiveData<CurrencyCallback> = MutableLiveData()
    var convertedToValue: ObservableFloat = ObservableFloat()
    var convertedFromValue: ObservableFloat = ObservableFloat()
    var spinnerFromPosition: ObservableInt = ObservableInt()
    var spinnerToPosition: ObservableInt = ObservableInt()
    var fromSpinnerArray: ObservableList<Array<String>> = ObservableArrayList()
    private val customViewModelScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    //
//    @Bindable
//    var spinnerFromPosition = ObservableField(0)
//        get() = spinnerFromPosition
//        set(value) {
//            field = value
//        }
//
//    @BindingAdapter("android:selection")
//    fun setListener(view: EditText, text : String) {
//        view.doAfterTextChanged {
//            it ->
//            if (it != null) {
//                view.setSelection(it.length)
//            }
//        }
//    }
    init {
        fetchAllCurrency()
    }

//        spinnerFromPosition.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                notifyPropertyChanged(BR.selectedItemPosition);
//            }
//        })
//    }
//
//    @BindingAdapter("onClick")
//    fun onClick(view: View, onClick: () -> Unit) {
//        view.setOnClickListener {
//            onClick()
//        }
//    }

//    @BindingAdapter("selectedItemPosition")
//    fun setSelectedItemPosition(view: AdapterView<*>, position: Int) {
//        if (view.selectedItemPosition != position) {
//            view.setSelection(position)
//            spinnerFromPosition.set(position)
//        }
//    }
//
//    @BindingAdapter(
//        value = ["android:onItemSelected", "android:onNothingSelected", "android:selectedItemPositionAttrChanged"],
//        requireAll = false
//    )
//    fun setOnItemSelectedListener(
//        view: AdapterView<*>, selected: OnItemSelected?,
//        nothingSelected: OnNothingSelected?, attrChanged: InverseBindingListener?
//    ) {
//        if (selected == null && nothingSelected == null && attrChanged == null) {
//            view.setOnItemSelectedListener(null)
//        } else {
//            view.setOnItemSelectedListener(
//                OnItemSelectedComponentListener(selected, nothingSelected, attrChanged)
//            )
//        }
//    }
//
//    @BindingAdapter("android:selectedValueAttrChanged")
//    fun setSelectedValueListener(
//        view: AdapterView<*>,
//        attrChanged: InverseBindingListener?
//    ) {
//        if (attrChanged == null) {
//            view.setOnItemSelectedListener(null)
//        } else {
//            view.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener() {
//                fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    attrChanged.onChange()
//                }
//
//                fun onNothingSelected(parent: AdapterView<*>?) {
//                    attrChanged.onChange()
//                }
//            })
//        }
//    }

    //    @BindingAdapter("android:selectedValue")
//    fun setSelectedValue(view: AdapterView<*>, selectedValue: Any) {
//        val adapter = view.adapter ?: return
//        // I haven't tried this, but maybe setting invalid position will
//        // clear the selection?
//        var position = AdapterView.INVALID_POSITION
//        for (i in 0 until adapter.count) {
//            if (adapter.getItem(i) === selectedValue) {
//                position = i
//                break
//            }
//        }
//        view.setSelection(position)
//    }
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
                        Log.i("spinnerdata", "spinnerdata")
                        //val arr = rateResponse.data?.let { CurrencyCallback.Success(it).success.rates.keys }
                        //  fromSpinnerArray.set(arr.toTypedArray())
                        //Log.i("spinnerdata", "spinnerdata" + fromSpinnerArray.value?.size)
                    }

                }
            }
        }
    }

    fun convert(fromAmount: String, toAmount: String, fromCurrency: String, toCurrency: String) {
        val fromDoubleAmount = fromAmount.toFloatOrNull()
        val toDoubleAmount = toAmount.toFloatOrNull()
        if (fromDoubleAmount == 0f && toDoubleAmount == 0f) {
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
                        var computedValue: Float

                        if (toDoubleAmount == 0f || toDoubleAmount == null) {
                            computedValue =
                                rateResponse.data.rates.getValue(toCurrency)
                                    .toInt() * fromDoubleAmount!!
                            convertedToValue?.set(computedValue)
                        } else {
                            computedValue =
                                (rateResponse.data.rates.getValue(fromCurrency)
                                    .toInt()) * toDoubleAmount!!
                            convertedFromValue?.set(computedValue)
                        }
                    }


                    //convertedToValue?.set(it)
                    (convertedCurrency as MutableLiveData).postValue(rateResponse.data?.let {
                        CurrencyCallback.Success(
                            it
                        )
                    })
                }
            }
        }
    }

    private fun handleNewSelection() {
        // val oldSelection = convertedCurrency.value?.rates?.entries?.indexOfFirst { it. }
        //  if (spinnerFromPosition.get() != oldSelection) {
        //   convertedCurrency.value?.rates[spinnerFromPosition.get()].onItemSelected()
    }

    fun swapCurrencies() {
        TODO("ForgotPasswordClicked")
    }

}