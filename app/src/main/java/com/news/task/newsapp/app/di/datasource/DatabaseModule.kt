package com.news.task.newsapp.app.di.datasource

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.news.task.newsapp.BuildConfig
import com.news.task.newsapp.data.local.db.database.NewsDatabase
//import com.news.task.newsapp.data.local.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NewsDatabase =
        Room.databaseBuilder(app, NewsDatabase::class.java, BuildConfig.NEWS_DB_NAME).build()
}