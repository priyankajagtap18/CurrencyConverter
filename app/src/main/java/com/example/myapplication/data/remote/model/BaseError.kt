package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * BaseError - Base error details
 */
data class BaseError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("info")
    val info: String
)
