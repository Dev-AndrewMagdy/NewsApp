package com.news.task.newsapp.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.news.task.newsapp.domain.utils.EmptyViewModel
import com.news.task.newsapp.domain.utils.Resource
import com.news.task.newsapp.presentation.viewstate.HomeNewsState

@Composable
fun UIStateManagement(
    state: HomeNewsState,
    modifier: Modifier = Modifier,
    emptyViewModel: EmptyViewModel? = null,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingState(emptyViewModel)
            state.error != null -> ErrorState(emptyViewModel)
            state.articles.isNotEmpty() -> {
                AnimatedVisibility(visible = true) {
                    content()
                }
            }
        }
    }
}

@Composable
fun LoadingState(emptyViewModel: EmptyViewModel?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = true) {
            emptyViewModel?.shimmerEffectView?.invoke()
        }
    }
}
@Composable
fun ErrorState(emptyViewModel: EmptyViewModel?) {
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        emptyViewModel?.image?.let {
            Image(
                painter = painterResource(id = emptyViewModel.image),
                alignment = Alignment.Center,
                contentDescription = "undraw_news"
            )
        }

        emptyViewModel?.errorMessage?.first?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                textAlign = TextAlign.Center
            )
        }

        emptyViewModel?.errorMessage?.second?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue),
                textAlign = TextAlign.Center
            )
        }
    }
}

