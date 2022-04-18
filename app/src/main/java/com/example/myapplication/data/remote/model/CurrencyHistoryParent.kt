package com.example.myapplication.data.remote.model

data class CurrencyHistoryParent(

    val date: String,
    val rates: List<CurrencyHistoryChild>,
//    val success: Boolean,
//    var baseError: BaseError,
)