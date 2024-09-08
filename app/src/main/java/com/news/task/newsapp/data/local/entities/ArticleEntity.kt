package com.news.task.newsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.task.newsapp.domain.utils.NewsCategory

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String, // Assuming URL is unique and can be used as a primary key
    val sourceId: String?,
    val category: String?,
    val country: String?,
    val sourceName: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)