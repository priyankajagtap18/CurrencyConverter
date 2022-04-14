package com.example.myapplication.data.remote.service

import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.data.remote.model.ResultWrapper
import com.example.myapplication.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ICurrencyService {

    @GET("latest")
    suspend fun getAllCurrency(
       // @Query("base") base: String = Constants.BASE_URL,
        @Query("access_key") accessKey: String = Constants.ACCESS_KEY,
      //  @Query("symbols") symbols: String = "IDR, USD, EUR",
    ) : Response<CurrencyResponse>
}