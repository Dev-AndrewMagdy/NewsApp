package com.news.task.newsapp.domain.usecases

import com.news.task.newsapp.domain.repository.NewsRepository
import com.news.task.newsapp.domain.utils.NewsCategory
import javax.inject.Inject

class GetNewsCategoriesUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(): List<NewsCategory> {
        return repository.getCategories()
    }
}