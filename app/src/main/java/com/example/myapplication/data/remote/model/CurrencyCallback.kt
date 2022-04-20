package com.example.myapplication.data.remote.model

/**
 * CurrencyCallback - Callback class for data posting from view model layer to view layer
 */
sealed class CurrencyCallback {
    class Success(val success: CurrencyResponse) : CurrencyCallback()
    class Failure(val errorText: ErrorWrapper) : CurrencyCallback()
    object Loading : CurrencyCallback()
}