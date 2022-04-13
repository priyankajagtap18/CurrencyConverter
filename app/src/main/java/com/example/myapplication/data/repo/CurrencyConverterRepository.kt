package com.example.myapplication.data.repo

import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.data.remote.service.ICurrencyService
import javax.inject.Inject

interface CurrencyConverterRepository {

    suspend fun getAllCurrency(base: String) : ResultWrapper<CurrencyResponse>
}

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val rService: ICurrencyService
)
: CurrencyConverterRepository {
    override suspend fun getAllCurrency(base: String): ResultWrapper<CurrencyResponse> {
        return try {
            val response = rService.getAllCurrency(base)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                ResultWrapper.Success(result)
            } else {
                ResultWrapper.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper.Error(e.message ?: "An error occured")
        }
    }

}