package com.news.task.newsapp.domain.helpers

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.news.task.newsapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun data() = flow {
        emit(Resource.Loading())

        val localData = loadFromDb().firstOrNull()

        if (shouldFetch(localData)) {

            emit(Resource.Loading(localData))

            try {
                val apiResponse = fetchFromNetwork()
                saveNetworkResult(processResponse(apiResponse))
                emitAll(loadFromDb().map { Resource.Success(it) })
            } catch (exception: Exception) {
                // Handle network failure gracefully
                if (localData == null) {
                    emit(Resource.Error("No cached data available and failed to fetch from network: ${exception.localizedMessage}"))
                } else {
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
            }
        } else {
            if (localData == null) {
                emit(Resource.Error("No cached data available for the selected category"))
            } else {
                emitAll(loadFromDb().map { Resource.Success(it) })
            }
        }
    }

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): RequestType

    @MainThread
    protected open fun processResponse(response: RequestType) = response
}
