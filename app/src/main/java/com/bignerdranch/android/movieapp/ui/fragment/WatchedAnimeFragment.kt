package com.bignerdranch.android.movieapp.ui.fragment

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel

@Composable
fun WatchedAnimeFragment(
    viewModel: MovieViewModel,
    navController: NavHostController
) {
    val watchedAnime by viewModel.watchedAnime.collectAsState()

    if (watchedAnime.isEmpty()) {
        Text(
            "Список просмотренных аниме пуст",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn {
            items(watchedAnime) { anime ->
                MovieItem(
                    movie = anime.toMovie(),
                    navController = navController
                )
            }
        }
    }
}