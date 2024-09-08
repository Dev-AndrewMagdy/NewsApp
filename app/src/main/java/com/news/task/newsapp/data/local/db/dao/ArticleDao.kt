package com.news.task.newsapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.task.newsapp.data.local.db.entities.ArticleEntity
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
    fun getNewsArticle(url: String) : Flow<ArticleEntity>
}
