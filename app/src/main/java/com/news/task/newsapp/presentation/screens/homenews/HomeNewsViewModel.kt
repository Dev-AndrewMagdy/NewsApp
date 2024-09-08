package com.news.task.newsapp.presentation.screens.homenews

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.task.newsapp.data.local.preferences.PreferencesManager
import com.news.task.newsapp.domain.usecases.GetNewsCategoriesUseCase
import com.news.task.newsapp.domain.usecases.GetNewsCountriesUseCase
import com.news.task.newsapp.domain.usecases.GetTopHeadlinesUseCase
import com.news.task.newsapp.domain.utils.NewsCategory
import com.news.task.newsapp.domain.utils.NewsCountries
import com.news.task.newsapp.domain.utils.Resource
import com.news.task.newsapp.presentation.intent.HomeNewsIntent
import com.news.task.newsapp.presentation.viewstate.HomeNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNewsViewModel @Inject constructor(
    private val topHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val newsCategoriesUseCase: GetNewsCategoriesUseCase,
    private val newsCountriesUseCase: GetNewsCountriesUseCase,
    private val preferences: PreferencesManager,
    private val connectivityManager: ConnectivityManager

) : ViewModel() {

    private val _state = MutableStateFlow(HomeNewsState())
    val state: StateFlow<HomeNewsState> = _state

    /**
     * //ALSO WE CAN DO IT BY CHANNEL INTENT
     *  val dataIntent = Channel<HomeNewsIntent>(Channel.UNLIMITED)
     *   val dataState = MutableStateFlow<HomeNewsState>(HomeNewsState.Inactive)
     * */

    init {
        loadCountries()
        loadCategories()
        handleIntent(HomeNewsIntent.SelectCategory(_state.value.selectedCategory))
    }

    fun handleIntent(intent: HomeNewsIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeNewsIntent.SelectCountry -> selectCountry(intent.country)
                is HomeNewsIntent.SelectCategory -> selectCategory(intent.category)
                is HomeNewsIntent.LoadNews -> loadNews()
            }
        }
    }

    private fun selectCountry(country: NewsCountries) {
        _state.update { it.copy(selectedCountry = country) }
        handleIntent(HomeNewsIntent.LoadNews)
    }

    private fun selectCategory(category: NewsCategory) {
        _state.update { it.copy(selectedCategory = category) }
        handleIntent(HomeNewsIntent.LoadNews)
    }

    private fun loadCountries() {
        if (!connectivityCheck()) {
            Log.d("connectivityCheck", preferences.category.value)
            _state.value.selectedCategory = preferences.category
        }
        _state.value.countries = newsCountriesUseCase.invoke()
    }

    private fun loadCategories() {
        if (!connectivityCheck()) {
            Log.d("connectivityCheck", preferences.country.value)
            _state.value.selectedCountry = preferences.country
        }
        _state.value.categories = newsCategoriesUseCase.invoke()
    }

    private suspend fun loadNews() {
        topHeadlinesUseCase(
            category = _state.value.selectedCategory,
            country = _state.value.selectedCountry
        ).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true, articles = emptyList()) }
                    Log.v("HomeNewsViewModel", "Loading")
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            articles = resource.data ?: emptyList()
                        )
                    }
                    Log.e("HomeNewsViewModel", "Success")

                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = resource.error) }
                    Log.d("HomeNewsViewModel", "Error")

                }

            }
        }

    }

    private fun connectivityCheck(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}