package com.bignerdranch.android.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bignerdranch.android.movieapp.MovieApp
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModelFactory


@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(
            (LocalContext.current.applicationContext as MovieApp).database
        )
    )
) {
    val movies by viewModel.movies.collectAsState()
    val filterState by viewModel.filterState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Поисковая строка и фильтры
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Поисковая строка
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.searchAnime(it) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Кнопка фильтра
            FilterDropdown(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                currentFilter = filterState,
                onFilterSelected = { type ->
                    expanded = false
                    when(type) {
                        "top" -> viewModel.loadTopMovies()
                        "recent" -> viewModel.loadRecentAnime()
                    }
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
fun FilterDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    currentFilter: String,
    onFilterSelected: (String) -> Unit
) {
    Box {
        // Кнопка для открытия меню
        OutlinedButton(
            onClick = { onExpandedChange(true) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500),
                contentColor = Color.White
            )
        ) {
            Text(
                text = when(currentFilter) {
                    "top" -> "Топ"
                    "recent" -> "Новинки"
                    else -> "Фильтр"
                }
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }

        // Выпадающее меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = { Text("Топ рейтинга") },
                onClick = { onFilterSelected("top") }
            )
            DropdownMenuItem(
                text = { Text("Новинки") },
                onClick = { onFilterSelected("recent") }
            )
        }
    }
}

