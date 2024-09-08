package com.news.task.newsapp.presentation.intent

sealed class ArticleIntent {
    data class LoadNewsArticle(val url :String) : ArticleIntent()

}