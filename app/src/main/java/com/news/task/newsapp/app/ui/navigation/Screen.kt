package com.news.task.newsapp.app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("HomeNewsScreen")
    object Detail : Screen("ArticleDetails/{message}") {
        fun createRoute(message: String) = "ArticleDetails/$message"
    }
}
