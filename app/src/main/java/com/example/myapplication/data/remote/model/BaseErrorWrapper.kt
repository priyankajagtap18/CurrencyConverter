package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * BaseErrorWrapper - Wrapper of base error
 */
data class BaseErrorWrapper(

    @SerializedName("success")
    var success: String,
    @SerializedName("error")
    var baseError: BaseError

)