package com.news.task.newsapp.domain.utils

sealed class Resource<T>(
    val data: T? = null,
    val error: Pair<String?,String?>? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Pair<String?,String?>?, data: T? = null) : Resource<T>(data, throwable)
}