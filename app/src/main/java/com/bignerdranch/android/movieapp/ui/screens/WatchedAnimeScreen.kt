package com.bignerdranch.android.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.movieapp.model.Movie
import com.bignerdranch.android.movieapp.model.Poster
import com.bignerdranch.android.movieapp.model.Rating
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun WatchedAnimeScreen(viewModel: MovieViewModel = viewModel()) {
    val watchedAnime by viewModel.watchedAnime.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (watchedAnime.isEmpty()) {
            Text(
                text = "Список просмотренных аниме пуст",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(watchedAnime) { anime ->
                    MovieItem(
                        movie = Movie(
                            id = anime.id,
                            name = anime.name,
                            poster = Poster(anime.posterUrl),
                            rating = Rating(anime.rating),
                            description = anime.description,
                            year = anime.year,
                            genres = anime.getGenres(),
                            type = null
                        )
                    )
                }
            }
        }
    }
}
