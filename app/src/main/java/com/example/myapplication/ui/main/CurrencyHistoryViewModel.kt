package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.data.repo.CurrencyConverterRepository
import com.example.myapplication.util.CurrencyExtension
import com.example.myapplication.util.getCalendarDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * CurrencyHistoryViewModel -
 * 1. History ViewModel to fetch historical data of past 3 days from API
 * 2. Filters history data in parent and child list
 */
@HiltViewModel
class CurrencyHistoryViewModel @Inject constructor(

    private val currencyConverterRepo: CurrencyConverterRepository,
) : ViewModel() {

    val currencyCallback: LiveData<CurrencyCallback> = MutableLiveData()
    val currencyHistory = MutableLiveData<List<CurrencyHistoryParent>>(emptyList())
    val clickEvent = MutableLiveData<CurrencyHistoryParent>()

    private val customViewModelScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    init {
        fetchCurrencyHistory()
    }

    private fun fetchCurrencyHistory() {
        (currencyCallback as MutableLiveData).value = CurrencyCallback.Loading
        customViewModelScope.launch {
            val day1History = async {
                currencyConverterRepo.getHistoricalData(
                    getCalendarDate(
                        CurrencyExtension.DATE_FORMAT_YYYY_MM_DD,
                        0
                    )
                )
            }
            val day2History = async {
                currencyConverterRepo.getHistoricalData(
                    getCalendarDate(
                        CurrencyExtension.DATE_FORMAT_YYYY_MM_DD,
                        1
                    )
                )
            }
            val day3History = async {
                currencyConverterRepo.getHistoricalData(
                    getCalendarDate(
                        CurrencyExtension.DATE_FORMAT_YYYY_MM_DD,
                        2
                    )
                )
            }

            val deferredResult = awaitAll(day1History, day2History, day3History)
            val parent: MutableList<CurrencyHistoryParent> = mutableListOf()
            getResult(deferredResult[0])?.let { parent.add(it) }
            getResult(deferredResult[1])?.let { parent.add(it) }
            getResult(deferredResult[2])?.let { parent.add(it) }
            currencyHistory.postValue(parent)
            withContext(Dispatchers.Main) {
                try {
                    if (!parent.isNullOrEmpty())
                        clickEvent.postValue(parent[0])
                } catch (e: Exception) {
                    CurrencyCallback.Failure(ErrorWrapper(throwable = Exception()))
                }
                if (parent.size == 0)
                    (currencyCallback).value =
                        CurrencyCallback.Failure(ErrorWrapper(throwable = Exception()))
            }
        }
    }

    private fun getResult(dayData: ResultWrapper<CurrencyResponse>): CurrencyHistoryParent? {
        val childList = dayData.data?.rates
        val rate: MutableList<CurrencyHistoryChild> = mutableListOf()
        childList?.forEach { (key, value) ->
            rate.add(CurrencyHistoryChild(key, value, null))
        }
        return dayData.data?.date?.let { CurrencyHistoryParent(it, rate, ::onHistoryClickListener) }
    }

    private fun onHistoryClickListener(detail: String) {
    }

}