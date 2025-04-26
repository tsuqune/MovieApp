package com.bignerdranch.android.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(viewModel: MovieViewModel = viewModel()) {
    val movies = viewModel.movies.collectAsState().value
    val filterState = viewModel.filterState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        // Панель фильтров
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterButton(
                text = "Топ рейтинга",
                isActive = filterState == "top",
                onClick = { viewModel.loadTopMovies() }
            )
            FilterButton(
                text = "Новинки",
                isActive = filterState == "recent",
                onClick = { viewModel.loadRecentAnime() }
            )
        }

        // Список
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) Color(0xFFFFA500) else Color.LightGray
        )
    ) {
        Text(text)
    }
}