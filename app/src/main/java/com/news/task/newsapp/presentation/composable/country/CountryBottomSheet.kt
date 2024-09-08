@file:OptIn(ExperimentalMaterialApi::class)
package com.news.task.newsapp.presentation.composable.country

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.news.task.newsapp.domain.utils.NewsCountries

@Composable
fun CountryBottomSheet(
    countries: List<NewsCountries>,
    selectedCategory: NewsCountries?,
    onCountrySelected: (NewsCountries) -> Unit,
    onDismiss: () -> Unit,
    modalState: ModalBottomSheetState
) {


    /*


     */

}
