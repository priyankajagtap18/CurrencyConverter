package com.example.myapplication.data.remote.model

sealed class ResultWrapper<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : ResultWrapper<T>(data, null)
    class Error<T>(message: String) : ResultWrapper<T>(null, message)
    class Loading<T>(message: String): ResultWrapper<T>(null, message)
}

//data class ResultWrapper1<T>(val status: Status, val data: T) {
//    companion object {
//        /** The type of data returned from the local database. */
//        fun <T> loading(data: T): ResultWrapper<T> = ResultWrapper(Status.LOADING, data)
//
//        /** The type of data returned from remote storage.*/
//        fun <T> success(data: T): ResultWrapper<T> = ResultWrapper(Status.SUCCESS, data)
//
//        /** The type of data currently saved in the local database after fetching the remote data has failed. */
////        fun <T> failure(data: T, throwable: Throwable): ResultWrapper<T> = ResultWrapper(Status.ERROR, data, throwable)
//        fun <T> failure(data: T): ResultWrapper<T> = ResultWrapper(Status.ERROR, data)
//
//        /** Returns a [Resource] with the provided arguments*/
//        fun <X> newResource(status: Status, data: X, throwable: Throwable?): ResultWrapper<X> = ResultWrapper(status, data)
//    }
//}

/**
 * Enum class containing the options of resource status.
 */
enum class Status {
    /** The success option for Resource. */
    SUCCESS,

    /** The error option for Resource. */
    ERROR,

    /** The loading option for Resource. */
    LOADING
}
