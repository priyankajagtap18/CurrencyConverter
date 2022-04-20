package com.example.myapplication.data.remote.model

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * ErrorWrapper -  Error class to fetch type of error caused and handle messages
 */
class ErrorWrapper {
    var code: Int = 0
    var message: String = ""
    lateinit var errorStatus: ErrorStatus

    constructor(throwable: Throwable) {
        when (throwable) {

            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {
                if (throwable.code() == 401) {
                    errorStatus = ErrorStatus.UNAUTHORIZED
                    code = throwable.code()
                    message = "Error"
                } else {
                    getHttpError(throwable.response()?.errorBody())
                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                errorStatus = ErrorStatus.TIMEOUT
                code = 0
                message = "Socket Timeout"
            }

            // handle connection error
            is IOException -> {
                errorStatus = ErrorStatus.NO_CONNECTION
                code = 0
                message = "Connection Error"
            }

            is UnknownHostException -> {
                errorStatus = ErrorStatus.NO_CONNECTION
                code = 0
                message = "Unknown host Error"
            }
            else -> {
                errorStatus = ErrorStatus.EMPTY_RESPONSE
                code = 1453
                message = "Sorry something went wrong"
            }
        }
    }

    constructor(responseBody: ResponseBody?) {
        getHttpError(responseBody)
    }


    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: ResponseBody?) {
        return try {
            // use response body to get error detail
            val result = body?.string()
            val json = Gson().fromJson(result, BaseErrorWrapper::class.java)
            errorStatus = ErrorStatus.BAD_RESPONSE
            code = 400
            message = json.baseError.info
        } catch (e: Throwable) {
            e.printStackTrace()
            errorStatus = ErrorStatus.NOT_DEFINED
            code = 0
            message = ""
        }

    }
}

enum class ErrorStatus {
    /**
     * error in connecting to repository (Server or Database)
     */
    NO_CONNECTION,

    /**
     * error in getting value (Json Error, Server Error, etc)
     */
    BAD_RESPONSE,

    /**
     * Time out  error
     */
    TIMEOUT,

    /**
     * no data available in repository
     */
    EMPTY_RESPONSE,

    /**
     * an unexpected error
     */
    NOT_DEFINED,

    /**
     * bad credential
     */
    UNAUTHORIZED
}