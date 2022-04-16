package com.example.myapplication.data.remote.model

sealed class CurrencyCallback {
    class Success(val success: CurrencyResponse) : CurrencyCallback()
    class Failure(val errorText: ErrorWrapper) : CurrencyCallback()
    object Loading : CurrencyCallback()
}