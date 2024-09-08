package com.news.task.newsapp.data.remote.networking

sealed class RequestState<T>(val data: T?, val errorResponse: ErrorType?, val message: String?) {
    class Success<T>(data: T?) : RequestState<T>(data, null,null)
    class Error<T>(errorType: ErrorType , message: String? = null) : RequestState<T>(null, errorType,message)
    class Loading<T> : RequestState<T>(null, null,null)
}

enum class ErrorType {
    REQUEST_TIMEOUT,
    EMPTY,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    CONNECTION_TIMEOUT,
    PAYLOAD_TOO_LARGE,
    UNAUTHENTICATED,
    SERVER_ERROR,
    SERIALIZATION_ERROR,
    UNKNOWN
}