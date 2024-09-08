package com.news.task.newsapp.presentation.composable.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.news.task.newsapp.data.local.db.entities.ArticleEntity
import com.news.task.newsapp.domain.utils.NewsCountries

@Composable
fun NewsItem(article: ArticleEntity, onClick: (ArticleEntity) -> Unit) {
    val radiusCorner = 8.dp

    Card(
        shape = RoundedCornerShape(radiusCorner),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke(article) },
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = article.urlToImage,
                    // contentScale = Scale.FILL
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(radiusCorner))
            )

            // Article details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.bodyLarge.copy(Color.Black),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp
                )

                Text(
                    text = article.description ?: "No Description",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
