package com.bignerdranch.android.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(viewModel: MovieViewModel = viewModel()) {
    val movies by viewModel.movies.collectAsState()
    val filterState by viewModel.filterState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Поисковая строка
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.searchAnime(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Панель фильтров
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterButton(
                text = "Топ рейтинга",
                isActive = filterState == "top" && searchQuery.isEmpty(),
                onClick = {
                    viewModel.loadTopMovies()
                    viewModel.searchAnime("")
                }
            )
            FilterButton(
                text = "Новинки",
                isActive = filterState == "recent" && searchQuery.isEmpty(),
                onClick = {
                    viewModel.loadRecentAnime()
                    viewModel.searchAnime("")
                }
            )
        }

        // Список или сообщение о пустом результате
        when {
            movies.isEmpty() && searchQuery.isNotEmpty() -> {
                Text(
                    text = "Ничего не найдено",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(movies) { movie ->
                        MovieItem(movie = movie)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Поиск аниме...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        singleLine = true
    )
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

