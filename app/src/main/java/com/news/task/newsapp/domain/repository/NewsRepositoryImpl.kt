package com.news.task.newsapp.domain.repository

import com.news.task.newsapp.data.local.db.database.NewsDatabase
import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.data.local.db.mapper.toEntity
import com.news.task.newsapp.data.local.preferences.PreferencesManager
import com.news.task.newsapp.data.remote.INewsServices
import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries
import com.news.task.newsapp.domain.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiNewsService: INewsServices,
    private val newsDatabase: NewsDatabase,
    private val preferences : PreferencesManager
) : NewsRepository
/** for handle api request multiple api call but in case one call only, BaseService()*/
{

    override suspend fun getTopHeadlines(
        category: NewsCategory,
        country: NewsCountries
    ): Flow<Resource<List<ArticleEntity>>> = flow {

        emit(Resource.Loading(null))
        delay(1000)

        //should fetch
        if (getLocalData(category.value, country.value).isNullOrEmpty()) {
            try {
                val response = apiNewsService.getTopHeadlines(category.value, country.value)
                val dataFromApi = response.body()?.articles?.map { it.toEntity(category.value, country.value) }

                if (dataFromApi != null && response.isSuccessful) {
                    newsDatabase.articleDao().deleteArticles()
                    newsDatabase.articleDao().insertArticles(dataFromApi)
                    preferences.deleteNewsPref()
                    preferences.category = category
                    preferences.country = country

                    emit(Resource.Success(getLocalData(category.value, country.value)!!))
                }
            } catch (throwable: Throwable) {
                emit(
                    Resource.Error(
                        /**We can pass custom exception and get string error msg from recourse*/
                        Pair(
                            "No News Found",
                            "News ${category.displayName} in country ${country.displayName} please check your connection to load last news"
                        ), null
                    )
                )

            }
        } else {
            emit(Resource.Success(getLocalData(category.value, country.value)!!))
        }
    }

    private suspend fun getLocalData(category: String, country: String): List<ArticleEntity>? {
        return newsDatabase.articleDao().getArticlesByCategory(category, country).firstOrNull()
    }

    override fun getCategories(): List<NewsCategory> {
        return NewsCategory.values().toList()
    }

    override fun getCountries(): List<NewsCountries> {
        return NewsCountries.values().toList()
    }

    override suspend fun getNewsArticle(url: String): Flow<Resource<ArticleEntity>> = flow {
        /**we can make base lambda exp to handle fetch data generally Local OR Api*/
        try {
            val article = newsDatabase.articleDao().getNewsArticle(url)
            if (article.firstOrNull() != null) {
                emit(Resource.Success(article.firstOrNull()!!))
            } else {
                emit(
                    Resource.Error(
                        Pair(
                            "No News Found",
                            ""
                        ), null
                    )
                )
            }

        } catch (throwable: Throwable) {
            emit(
                Resource.Error(
                    Pair(
                        "No News Found", throwable.message!!
                    ), null
                )
            )
        }

    }

}
