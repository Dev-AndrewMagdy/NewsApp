package com.news.task.newsapp.domain.usecases

import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.domain.repository.NewsRepository
import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries
import com.news.task.newsapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(category: NewsCategory, country: NewsCountries): Flow<Resource<List<ArticleEntity>>> {
        return repository.getTopHeadlines(category,country)
    }
}