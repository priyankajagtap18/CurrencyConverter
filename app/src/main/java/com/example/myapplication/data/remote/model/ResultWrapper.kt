package com.example.myapplication.data.remote.model

sealed class ResultWrapper<T>(val data: T?, val error: ErrorWrapper?) {
    class Success<T>(data: T) : ResultWrapper<T>(data, null)
    class Error<T>(error: ErrorWrapper) : ResultWrapper<T>(null, error)
    class Loading<T>() : ResultWrapper<T>(null, null)
}

