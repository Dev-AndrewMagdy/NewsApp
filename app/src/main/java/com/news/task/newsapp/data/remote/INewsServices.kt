package com.news.task.newsapp.data.remote

import com.news.task.newsapp.domain.model.ArticleDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface INewsServices {

    @GET("top-headlines/category/{category}/{countryCode}.json")
    suspend fun getTopHeadlines(@Path("category") category: String?, @Path("countryCode") country: String?) : Response<ArticleDTO>

    @GET("everything/{source_id}.json")
    suspend fun getNewsBySource(@Path("source_id") sourceId: String?) : Response<List<ArticleDTO>>

    @GET("sources.json")
    suspend fun getSources()
}