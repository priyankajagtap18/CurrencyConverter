package com.example.myapplication.data.remote.service

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}