package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * CurrencyResponse - Detailed Response of all currency data in LinkedHashmap of Symbol and rates, and other details
 */
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
    val timestamp: Int,
    @SerializedName("error")
    var baseError: BaseError,
    @SerializedName("historical")
    var historical: Boolean
)