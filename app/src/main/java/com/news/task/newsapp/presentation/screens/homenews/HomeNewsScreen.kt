package com.news.task.newsapp.presentation.screens.homenews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.news.task.newsapp.R
import com.news.task.newsapp.app.ui.navigation.Screen
import com.news.task.newsapp.domain.utils.EmptyViewModel
import com.news.task.newsapp.presentation.composable.CountryBottomSheet
import com.news.task.newsapp.presentation.composable.HomeNewsEffect
import com.news.task.newsapp.presentation.composable.UIStateManagement
import com.news.task.newsapp.presentation.composable.country.CountryItem
import com.news.task.newsapp.presentation.composable.news.NewsItem
import com.news.task.newsapp.presentation.composable.newscategory.CategoryList
import com.news.task.newsapp.presentation.intent.HomeNewsIntent
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNewsScreen(navController: NavController, viewModel: HomeNewsViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    var countrySheetVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(10.dp)
    ) {

        CategoryList(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            onCategorySelected = {
                viewModel.handleIntent(HomeNewsIntent.SelectCategory(it))
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.top_news_head_lines),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(10.dp)
            )

            IconButton(
                onClick = {
                    countrySheetVisibility = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Select Country"
                )
            }

        }

        UIStateManagement(
            state = state,
            emptyViewModel = EmptyViewModel(
                image = R.drawable.undraw_news,
                errorMessage = state.error,
                shimmerEffectView = { HomeNewsEffect() })
        ) {
            LazyColumn(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.articles) { article ->
                    NewsItem(article) {
                        val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())
                        navController.navigate(Screen.Detail.createRoute(encodedUrl))
                    }
                }
            }

            /*when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text("Error: ${state.error}")
                state.articles.isNotEmpty() -> LazyColumn {
                    items(state.articles) { article ->
                        NewsItem(article) {
                            navController.navigate(Screens.Detail.createRoute(article.sourceName!!))
                            // navController.navigate("${Constance.DETAILS_ROUTE}/andrew")
                        }
                    }
                }
            }
*/
            CountryBottomSheet(
                showSheet = countrySheetVisibility,
                modifier = Modifier.navigationBarsPadding(),
                onDismissRequest = { countrySheetVisibility = false }
            ) {

                LazyColumn(
                    modifier = Modifier.padding(21.dp)
                ) {
                    items(state.countries) { country ->
                        CountryItem(country = country, isSelected = state.selectedCountry == country) {
                            viewModel.handleIntent(HomeNewsIntent.SelectCountry(it))
                            countrySheetVisibility = false
                        }
                        Spacer(Modifier.padding(bottom = 15.dp))
                    }
                }
                Spacer(Modifier.padding(bottom = 40.dp))
            }
        }

    }

}

