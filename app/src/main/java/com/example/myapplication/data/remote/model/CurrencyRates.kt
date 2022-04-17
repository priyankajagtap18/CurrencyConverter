package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencyRates(

    val symbol: String,
    val rate: Float
)