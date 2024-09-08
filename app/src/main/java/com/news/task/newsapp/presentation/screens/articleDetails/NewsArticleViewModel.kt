package com.news.task.newsapp.presentation.screens.articleDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.task.newsapp.domain.usecases.GetNewsArticleUseCase
import com.news.task.newsapp.domain.utils.Resource
import com.news.task.newsapp.presentation.intent.ArticleIntent
import com.news.task.newsapp.presentation.viewstate.ArticleViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsArticleViewModel @Inject constructor(private val getNewsArticleUseCase: GetNewsArticleUseCase) : ViewModel() {

    private val _state = MutableStateFlow<ArticleViewState>(ArticleViewState.Loading)
    val state: StateFlow<ArticleViewState> = _state

    /**USING CHANNEL INTENT*/

    private val dataIntent = Channel<ArticleIntent>(Channel.UNLIMITED)

    fun handleIntent(loadNewsArticle: ArticleIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            dataIntent.send(loadNewsArticle)
            dataIntent.consumeAsFlow().collect {
                when (it) {
                    is ArticleIntent.LoadNewsArticle -> loadNewsArticle(it.url)
                }
            }
        }
    }

    private suspend fun loadNewsArticle(url: String) {
        getNewsArticleUseCase.invoke(url).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = ArticleViewState.Loading
                }

                is Resource.Success -> {
                    _state.value = ArticleViewState.Success(resource.data!!)
                }

                is Resource.Error -> {
                    _state.value = ArticleViewState.Error(resource.error!!)

                }
            }
        }
    }
}