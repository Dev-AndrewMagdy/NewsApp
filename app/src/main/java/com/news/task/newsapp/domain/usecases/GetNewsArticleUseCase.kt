package com.news.task.newsapp.domain.usecases

import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.domain.repository.NewsRepository
import com.news.task.newsapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsArticleUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(url: String): Flow<Resource<ArticleEntity>> {
        return repository.getNewsArticle(url)
    }
}