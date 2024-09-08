package com.news.task.newsapp.presentation.viewstate

import com.news.task.newsapp.data.local.db.entities.ArticleEntity

sealed class ArticleViewState {
    object Loading : ArticleViewState()
    data class Success(val article: ArticleEntity) : ArticleViewState()
    data class Error(val message: Pair<String?,String?>) : ArticleViewState()

}