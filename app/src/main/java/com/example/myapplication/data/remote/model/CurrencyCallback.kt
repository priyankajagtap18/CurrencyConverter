package com.example.myapplication.data.remote.model

sealed class CurrencyCallback {
    class Success(val success: String) : CurrencyCallback()
    class Failure(val errorText: String) : CurrencyCallback()
    object Loading : CurrencyCallback()
}