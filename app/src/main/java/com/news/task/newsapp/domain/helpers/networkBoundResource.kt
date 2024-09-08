package com.news.task.newsapp.domain.helpers

import com.news.task.newsapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,               // Query data from Room
    crossinline fetch: suspend () -> RequestType,            // Fetch data from network
    crossinline saveFetchResult: suspend (RequestType) -> Unit,  // Save network result into Room
    crossinline shouldFetch: (ResultType?) -> Boolean = { true } // Decide if fetching from network is necessary
) = flow {
    emit(Resource.Loading(null)) // Emit loading state
    val data = query().firstOrNull() // Get current data from Room

    val flow = if (shouldFetch(data)) {   // Check if we need to fetch from network
        try {
            saveFetchResult(fetch())      // Fetch from network and save to Room
            query().map { Resource.Success(it) } // Emit fresh data from Room after network call
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable.message!!, it) } // Emit error and cached data
        }
    } else {
        query().map { Resource.Success(it) }  // Emit cached data if no fetch needed
    }

    emitAll(flow)
}
