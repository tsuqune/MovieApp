package com.bignerdranch.android.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bignerdranch.android.movieapp.model.Movie

class AnimeDetailActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = intent.getParcelableExtra<Movie>("movie")
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (movie != null) {
                        AnimeDetailScreen(movie = movie)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeDetailScreen(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Постер
        AsyncImage(
            model = movie.poster?.url,
            contentDescription = "Постер",
            modifier = Modifier
                .size(450.dp)
                .clip(RoundedCornerShape(170.dp))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Название
        Text(
            text = movie.name ?: "Название отсутствует",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Рейтинг
        Text(
            text = "★ ${movie.rating?.kp?.formatRating() ?: "Н/Д"}",
            color = Color(0xFFFFA500),
            style = MaterialTheme.typography.titleLarge
        )

        // Год
        Text(
            text = "Год: ${movie.year ?: "—"}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Жанры
        Text(
            text = "Жанры: ${movie.genres?.joinToString { it.name ?: "" } ?: "Не указаны"}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Описание
        Text(
            text = movie.description ?: "Описание отсутствует",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

fun Double.formatRating(): String {
    return if (this % 1 == 0.0) {
        this.toInt().toString()
    } else {
        "%.1f".format(this)
            .replace(".0", "")
            .replace(",0", "")
    }
}


