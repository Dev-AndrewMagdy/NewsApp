package com.news.task.newsapp.data.local.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.task.newsapp.domain.utils.NewsCategory
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
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
) : Parcelable