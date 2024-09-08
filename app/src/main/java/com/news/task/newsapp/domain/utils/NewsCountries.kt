package com.news.task.newsapp.domain.utils

enum class NewsCountries(override val value: String, val displayName: String) : ValueEnum<String> {
    USA("us", "USA"),
    INDIA("in", "India"),
    AUSTRALIA("au", "Australia"),
    RUSSIA("ru", "Russia"),
    FRANCE("fr", "France"),
    UNITED_KINGDOM("gb", "United Kingdom");

}