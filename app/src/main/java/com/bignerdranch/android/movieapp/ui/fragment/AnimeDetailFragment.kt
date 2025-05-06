package com.bignerdranch.android.movieapp.ui.fragment

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.bignerdranch.android.movieapp.formatRating
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bignerdranch.android.movieapp.model.Movie
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel

@Composable
fun AnimeDetailFragment(
    movieId: String,
    viewModel: MovieViewModel,
    navController: NavHostController
) {
    val movie by viewModel.getMovieById(movieId).collectAsState()
    val watchedList by viewModel.watchedAnime.collectAsState()

    LaunchedEffect(movieId) {
        if (movie == null) {
            viewModel.loadMovieDetails(movieId)
        }
    }

    movie?.let { movie ->
        AnimeDetailScreen(
            movie = movie,
            isWatched = watchedList.any { it.id == movie.id },
            onBack = { navController.popBackStack() },
            onToggleWatched = { viewModel.toggleWatchedStatus(movie) }
        )
    } ?: Text("Загрузка...")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimeDetailScreen(
    movie: Movie,
    isWatched: Boolean,
    onBack: () -> Unit,
    onToggleWatched: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Постер
                AsyncImage(
                    model = movie.poster?.url,
                    contentDescription = "Постер",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Рейтинг и год
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${movie.rating?.imdb?.formatRating() ?: "Н/Д"}",
                        color = Color(0xFFFFA500),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Text(
                        text = "Год: ${movie.year ?: "—"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Жанры
                Text(
                    text = "Жанры: ${movie.genres?.joinToString { it.name ?: "" } ?: "Не указаны"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )

                // Описание
                Text(
                    text = movie.description ?: "Описание отсутствует",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )

                // Кнопка "Просмотрено"
                Button(
                    onClick = onToggleWatched,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isWatched) Color.Green else Color.Red
                    )
                ) {
                    Text(if (isWatched) "Просмотрено ✓" else "Не просмотрено")
                }
            }
        }
    )
}