package com.news.task.newsapp.domain.usecases

import com.news.task.newsapp.domain.repository.NewsRepository
import com.news.task.newsapp.domain.utils.NewsCountries
import javax.inject.Inject

class GetNewsCountriesUseCase @Inject constructor(private val repository: NewsRepository) {

    operator fun invoke(): List<NewsCountries>{
        return repository.getCountries()
    }

}