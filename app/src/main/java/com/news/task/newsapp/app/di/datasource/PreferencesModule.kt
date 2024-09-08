package com.news.task.newsapp.app.di.datasource

import android.content.Context
import com.news.task.newsapp.data.local.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    internal fun provideSharedPreferences(@ApplicationContext application: Context): PreferencesManager {
        return PreferencesManager(application = application)
    }
}