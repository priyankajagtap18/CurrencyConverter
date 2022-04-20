package com.example.myapplication.data.remote.model

/**
 * CurrencyHistoryChild - Data class of Child list of each day in form of symbol, rate and per rate value
 */
data class CurrencyHistoryChild(
    val symbol: String,
    val rate: Float,
    val detail: String?
)