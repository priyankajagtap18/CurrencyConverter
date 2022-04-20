package com.example.myapplication.data.remote.model

data class CurrencyHistoryParent(

    val date: String,
    val rates: List<CurrencyHistoryChild>,
    private val onItemClick: (String) -> Unit
//    val success: Boolean,
//    var baseError: BaseError,
) {
    fun onClick() {
        onItemClick("$date $rates")
    }
}