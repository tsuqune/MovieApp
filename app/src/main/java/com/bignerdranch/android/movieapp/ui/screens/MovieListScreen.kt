package com.bignerdranch.android.movieapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(viewModel: MovieViewModel = viewModel()) {
    val movies = viewModel.movies.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieItem(movie = movie)
        }
    }
}