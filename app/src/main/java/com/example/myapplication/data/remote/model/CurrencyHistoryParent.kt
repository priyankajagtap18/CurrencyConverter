package com.example.myapplication.data.remote.model

/**
 *CurrencyHistoryParent - Data class for holding History information of all dates and sublist of symbols and rates
 */
data class CurrencyHistoryParent(

    val date: String,
    val rates: List<CurrencyHistoryChild>,
    private val onItemClick: (String) -> Unit

) {
    fun onClick() {
        onItemClick("$date $rates")
    }
}