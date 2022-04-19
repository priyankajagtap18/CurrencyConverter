package com.example.myapplication.data.remote.model

data class CurrencyHistoryChild(
    val symbol: String,
    val rate: Float,
    val detail: String?
)