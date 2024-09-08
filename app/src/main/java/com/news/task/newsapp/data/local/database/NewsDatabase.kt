package com.news.task.newsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.task.newsapp.data.local.dao.ArticleDao
import com.news.task.newsapp.data.local.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}
