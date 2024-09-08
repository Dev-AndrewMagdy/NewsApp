package com.news.task.newsapp.data.remote

import android.util.Log
import com.google.gson.JsonParseException
import com.news.task.newsapp.data.remote.networking.ErrorType
import com.news.task.newsapp.data.remote.networking.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseService {

    suspend fun <T> executeApi(requestHandler: MutableStateFlow<RequestState<T>>, call: suspend () -> Response<T>): T? {
        try {
            requestHandler.emit(RequestState.Loading())
            val response = call.invoke()
            val body = response.body()
            var data: T? = null

            Log.d(this::class.simpleName, "executeApi: response $response")

            if (body is Collection<*> && (body as Collection<*>).isEmpty()) {

                Log.d(this::class.simpleName, "executeApi: empty")

                requestHandler.emit(RequestState.Error(ErrorType.EMPTY))

            } else {
                Log.d(this::class.simpleName, "executeApi: success")
                data = body
                requestHandler.emit(RequestState.Success(data))
            }
            return data

        } catch (e: IOException) {
            requestHandler.emit(RequestState.Error(ErrorType.NO_INTERNET))

        } catch (e: SocketTimeoutException) {

            requestHandler.emit(RequestState.Error(ErrorType.CONNECTION_TIMEOUT))

        } catch (e: HttpException) {
            when (e.code()) {
                408 -> requestHandler.emit(RequestState.Error(ErrorType.REQUEST_TIMEOUT))
                413 -> requestHandler.emit(RequestState.Error(ErrorType.PAYLOAD_TOO_LARGE))
                401 -> requestHandler.emit(RequestState.Error(ErrorType.UNAUTHENTICATED))
                in 500..599 -> requestHandler.emit(RequestState.Error(ErrorType.SERVER_ERROR))
                else -> requestHandler.emit(RequestState.Error(ErrorType.UNKNOWN))
            }
        } catch (e: JsonParseException) {
            requestHandler.emit(RequestState.Error(ErrorType.SERIALIZATION_ERROR))
        } catch (e: Exception) {
            requestHandler.emit(RequestState.Error(ErrorType.UNKNOWN, e.message.toString()))
        }
        return null
    }
}