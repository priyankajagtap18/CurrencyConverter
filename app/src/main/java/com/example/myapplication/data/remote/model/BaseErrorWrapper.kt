package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class BaseErrorWrapper(

    @SerializedName("success")
    var success: String,
    @SerializedName("error")
    var baseError: BaseError

)