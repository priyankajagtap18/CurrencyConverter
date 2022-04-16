package com.example.myapplication.di

import com.example.myapplication.data.remote.service.ICurrencyService
import com.example.myapplication.data.repo.CurrencyConverterRepository
import com.example.myapplication.data.repo.CurrencyConverterRepositoryImpl
import com.example.myapplication.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): ICurrencyService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ICurrencyService::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: ICurrencyService): CurrencyConverterRepository =
        CurrencyConverterRepositoryImpl(api)

}