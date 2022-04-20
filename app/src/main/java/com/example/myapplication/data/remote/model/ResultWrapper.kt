package com.example.myapplication.data.remote.model

/**
 * ResultWrapper - Wrapper class to generalize data transformation from repository layer to view model classes
 */
sealed class ResultWrapper<T>(val data: T?, val error: ErrorWrapper?) {
    class Success<T>(data: T) : ResultWrapper<T>(data, null)
    class Error<T>(error: ErrorWrapper) : ResultWrapper<T>(null, error)
    class Loading<T>() : ResultWrapper<T>(null, null)
}

