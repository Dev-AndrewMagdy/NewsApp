package com.news.task.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.task.newsapp.data.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE category = :category AND country = :country")
    fun getArticlesByCategory(category: String, country:String): Flow<List<ArticleEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)
    @Query("DELETE FROM articles")
    suspend fun deleteArticles()
    @Query("SELECT * FROM articles WHERE url = :url")
    suspend fun getNewsArticle(url: String) :ArticleEntity
}
