package com.news.task.newsapp.presentation.screens.articleDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.news.task.newsapp.presentation.viewstate.ArticleViewState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleDetailsScreen(
    navController: NavHostController,
    viewModel: NewsArticleViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Article Details")
            },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (state) {

                is ArticleViewState.Success -> {
                    val context = LocalContext.current

                    val article = (state as ArticleViewState.Success).article
                    Image(
                        painter = rememberImagePainter(article.urlToImage),
                        contentDescription = null,
                        modifier = Modifier

                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = article.title ?: "No Title available",
                        style = MaterialTheme.typography.h6
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = article.description ?: "No description available")

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = { shareArticleLink(context, article.url) }) {
                            Text(text = "Share")
                        }

                        // Open in browser button
                        Button(onClick = { openArticleInBrowser(context, article.url) }) {
                            Text(text = "Open in Browser")
                        }
                    }
                }

                else -> {}

            }

        }
    }
}

fun shareArticleLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }

    val chooser = Intent.createChooser(intent, "Share Article via")
    ContextCompat.startActivity(context, chooser, null)
}

fun openArticleInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    ContextCompat.startActivity(context, intent, null)
}

