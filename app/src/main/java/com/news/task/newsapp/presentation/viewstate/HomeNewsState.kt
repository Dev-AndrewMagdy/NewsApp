package com.news.task.newsapp.presentation.viewstate

import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries

data class HomeNewsState(
    val isLoading: Boolean = false,
    var categories: List<NewsCategory> = emptyList(),
    var countries: List<NewsCountries> = emptyList(),
    val articles: List<ArticleEntity> = emptyList(),
    var selectedCategory: NewsCategory = NewsCategory.GENERAL,
    var selectedCountry: NewsCountries = NewsCountries.USA,
    val error: Pair<String?, String?>? = null
)