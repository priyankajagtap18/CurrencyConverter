package com.example.myapplication.data.repo

import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.data.remote.model.ErrorWrapper
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.remote.service.ICurrencyService
import javax.inject.Inject

interface CurrencyConverterRepository {

    suspend fun getAllCurrency(): ResultWrapper<CurrencyResponse>

    suspend fun convertCurrency(
        fromSymbol: String,
        toSymbol: String
    ): ResultWrapper<CurrencyResponse>
}

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val rService: ICurrencyService
) : CurrencyConverterRepository {
    override suspend fun getAllCurrency(): ResultWrapper<CurrencyResponse> {
        return try {
            val response = rService.getAllCurrency()
            val result = response.body()

            if (response.isSuccessful && result != null) {
                ResultWrapper.Success(result)
            } else {
                ResultWrapper.Error(ErrorWrapper(response.errorBody()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Error(ErrorWrapper(e))
        }
    }

    override suspend fun convertCurrency(
        fromSymbol: String,
        toSymbol: String
    ): ResultWrapper<CurrencyResponse> {
        return try {
            val response = rService.convertCurrency(fromSymbol)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                ResultWrapper.Success(result)
            } else {
                ResultWrapper.Error(ErrorWrapper(response.errorBody()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Error(ErrorWrapper(e))
        }
    }


}