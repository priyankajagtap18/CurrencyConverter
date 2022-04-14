package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: LinkedHashMap<String, Float>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Int
)