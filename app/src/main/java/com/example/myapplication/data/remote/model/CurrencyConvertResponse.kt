package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencyConvertResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("query")
    val rates: HashMap<String, Double>,
    @SerializedName("info")
    val info: HashMap<String, Double>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: Double
)