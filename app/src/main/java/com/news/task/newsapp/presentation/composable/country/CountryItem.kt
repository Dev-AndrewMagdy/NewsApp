package com.news.task.newsapp.presentation.composable.country

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.news.task.newsapp.domain.utils.NewsCountries
import java.util.Locale

@Composable
fun CountryItem(
    country: NewsCountries,
    isSelected: Boolean,
    onClick: (NewsCountries) -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick(country) }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Country flag image
        Image(
            painter = rememberAsyncImagePainter(

                model = "https://flagsapi.com/${country.value.toUpperCase(Locale.ROOT)}/shiny/64.png",
                contentScale = ContentScale.Crop
            ),
            contentDescription = "${country.name} flag",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )

        // Country name
        Text(
            text = country.displayName,
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
