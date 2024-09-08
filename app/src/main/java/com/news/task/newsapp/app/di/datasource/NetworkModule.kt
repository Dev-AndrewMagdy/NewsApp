package com.news.task.newsapp.app.di.datasource

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.news.task.newsapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val writeTimeout = 30L
    private val connectTimeout = 60L
    private val readTimeout = 30L
    private val cacheSize = 10 * 1024 * 1024L // 10 MiB

    @Provides
    @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            //  .addInterceptor(CustomInterceptor(shared))
            .addInterceptor(loggingInterceptor).cache(cache)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    // @Named(AppConstants.ABC_BACKEND_RETROFIT)
    internal fun provideMyBackendRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.BASE_URL)
        builder.client(okHttpClient)
        builder.addConverterFactory(GsonConverterFactory.create())
        return builder.build()
    }

    inner class CustomInterceptor internal constructor() : Interceptor, IOException() {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
             request = request.newBuilder()
                 .header("Content-Type", "application/json")
                 .build()
            val response = chain.proceed(request)
            response.code
            return response
        }
    }
}