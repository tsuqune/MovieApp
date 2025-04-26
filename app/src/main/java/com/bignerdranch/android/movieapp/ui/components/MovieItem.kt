package com.bignerdranch.android.movieapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bignerdranch.android.movieapp.model.Movie

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Постер
            movie.poster?.url?.let { url ->
                Image(
                    painter = rememberImagePainter(
                        data = url,
                        builder = { crossfade(true) }
                    ),
                    contentDescription = movie.name,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
                println(">>> Загружен постер: $url") // Лог
            } ?: println(">>> Постер отсутствует")

            Spacer(modifier = Modifier.width(8.dp))

            // Информация
            Column {
                Text(
                    text = movie.name ?: "Без названия",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "★ ${movie.rating?.kp ?: "Н/Д"}",
                    color = Color(0xFFFFA500)
                )
                Text(
                    text = movie.description ?: "Описание отсутствует",
                    maxLines = 3
                )
            }
        }
    }
}