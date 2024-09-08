package com.news.task.newsapp.presentation.composable.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.news.task.newsapp.data.local.entities.ArticleEntity

@Composable
fun NewsList(articles: List<ArticleEntity>) {

    LazyColumn {
        items(articles) { article ->
            NewsItem(article) {
                // Handle article click, e.g., navigate to details
            }
        }
    }
}