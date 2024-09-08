package com.news.task.newsapp.presentation.composable.newscategory

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.news.task.newsapp.domain.utils.NewsCategory

@Composable
fun CategoryList(
    categories: List<NewsCategory>,
    selectedCategory: NewsCategory?,
    onCategorySelected: (NewsCategory) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = selectedCategory == category,
                onCategoryClick = onCategorySelected
            )
        }
    }
}
