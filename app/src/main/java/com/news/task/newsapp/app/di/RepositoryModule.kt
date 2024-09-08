package com.news.task.newsapp.app.di

import com.news.task.newsapp.data.local.db.database.NewsDatabase
import com.news.task.newsapp.data.local.preferences.PreferencesManager
import com.news.task.newsapp.data.remote.INewsServices
import com.news.task.newsapp.domain.repository.NewsRepository
import com.news.task.newsapp.domain.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        retrofit: Retrofit,
        newsDatabase: NewsDatabase,
        preferencesManager: PreferencesManager
    ): NewsRepository {
        return NewsRepositoryImpl(
            retrofit.create(INewsServices::class.java),
            newsDatabase,
            preferencesManager
        )
    }
}