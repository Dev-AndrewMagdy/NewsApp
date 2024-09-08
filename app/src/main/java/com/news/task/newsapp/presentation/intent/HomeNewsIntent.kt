package com.news.task.newsapp.presentation.intent

import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries

sealed class HomeNewsIntent {
    data class SelectCountry(val country: NewsCountries) : HomeNewsIntent()
    data class SelectCategory(val category: NewsCategory) : HomeNewsIntent()
    object LoadNews : HomeNewsIntent()
}
