package com.news.task.newsapp.domain.utils

import androidx.compose.runtime.Composable

data class EmptyViewModel(
    val image: Int? = null,
    val errorMessage: Pair<String?,String?>? = null,
    val description: String? = null,
    val actionText: String? = null,
    val action: (() -> Unit)? = null,
    val shimmerEffectView: @Composable () -> Unit
)