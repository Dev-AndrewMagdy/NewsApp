package com.news.task.newsapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Preview
@Composable
fun HomeNewsEffect() {
    val spacePadding = 9.dp
    val cornerRadius = 12.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = spacePadding)
            .shimmer()
    ) {
        items(6) { _ ->
            Box(
                modifier = Modifier
                    .padding(spacePadding)
                    .fillMaxWidth()
                    .height(height = 100.dp)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color.Gray)
            )
        }
    }
}