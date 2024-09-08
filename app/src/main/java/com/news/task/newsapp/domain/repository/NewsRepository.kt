package com.news.task.newsapp.domain.repository

import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries
import com.news.task.newsapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getTopHeadlines(category: NewsCategory, country: NewsCountries): Flow<Resource<List<ArticleEntity>>>
    suspend fun getNewsArticle(url: String): Flow<Resource<ArticleEntity>>
    /** As static dont need to Flow data*/
    fun getCategories() : List<NewsCategory>
    /** As static dont need to Flow data*/
    fun getCountries(): List<NewsCountries>

}