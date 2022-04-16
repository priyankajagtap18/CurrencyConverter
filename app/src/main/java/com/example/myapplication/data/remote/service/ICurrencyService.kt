package com.example.myapplication.data.remote.service

import com.example.myapplication.data.remote.model.CurrencyResponse
import com.example.myapplication.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ICurrencyService {

    @GET("latest")
    suspend fun getAllCurrency(
        @Query("access_key") accessKey: String = Constants.ACCESS_KEY,
    ): Response<CurrencyResponse>

    @GET("latest")
    suspend fun convertCurrency(
        @Query("base") base: String,
        @Query("access_key") accessKey: String = Constants.ACCESS_KEY,
    ): Response<CurrencyResponse>

    @GET("{date}")
    suspend fun getHistoricalData(
        @Path("date") date: String,
        @Query("access_key") accessKey: String = Constants.ACCESS_KEY,
    ): Response<CurrencyResponse>
}