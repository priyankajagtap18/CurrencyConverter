package com.example.myapplication.data.remote.model

sealed class ResultWrapper<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : ResultWrapper<T>(data, null)
    class Error<T>(message: String) : ResultWrapper<T>(null, message)
    class Loading<T>(message: String): ResultWrapper<T>(null, message)

}

