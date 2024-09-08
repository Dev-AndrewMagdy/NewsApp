package com.news.task.newsapp.domain.utils

enum class NewsCategory(override val value: String, val displayName: String) : ValueEnum<String> {
    GENERAL("general", "General"),
    BUSINESS("business", "Business"),
    ENTERTAINMENT("entertainment", "Entertainment"),
    HEALTH("health", "Health"),
    SCIENCE("science", "Science"),
    SPORTS("sports", "Sports"),
    TECHNOLOGY("technology", "Technology");

}