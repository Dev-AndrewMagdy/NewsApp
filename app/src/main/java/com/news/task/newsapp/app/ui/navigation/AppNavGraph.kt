package com.news.task.newsapp.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.news.task.newsapp.presentation.intent.ArticleIntent
import com.news.task.newsapp.presentation.screens.articleDetails.ArticleDetailsScreen
import com.news.task.newsapp.presentation.screens.articleDetails.NewsArticleViewModel
import com.news.task.newsapp.presentation.screens.homenews.HomeNewsScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeNewsScreen(navController)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val articleUrl = backStackEntry.arguments?.getString("message")
            val viewModel = hiltViewModel<NewsArticleViewModel>()

            viewModel.handleIntent(ArticleIntent.LoadNewsArticle(articleUrl!!))
            ArticleDetailsScreen(viewModel = viewModel, navController = navController)
        }
    }
}
