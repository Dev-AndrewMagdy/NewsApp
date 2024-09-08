package com.news.task.newsapp.app.ui.navigation

sealed class Screens(val route: String) {
    object Home : Screens("HomeNewsScreen")
    object Detail : Screens("ArticleDetails/{message}") {
        fun createRoute(message: String) = "ArticleDetails/$message"
    }
}
