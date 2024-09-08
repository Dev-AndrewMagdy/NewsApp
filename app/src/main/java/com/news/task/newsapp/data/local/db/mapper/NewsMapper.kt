package com.news.task.newsapp.data.local.db.mapper

import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.domain.model.ArticleDTO

fun ArticleDTO.Articles.toEntity(category: String, country: String): ArticleEntity {
    return ArticleEntity(
        url = this.url ?: "",
        sourceId = this.source?.id,
        sourceName = this.source?.name,
        author = this.author,
        title = this.title,
        description = this.description,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
        category = category,
        country = country
    )
}