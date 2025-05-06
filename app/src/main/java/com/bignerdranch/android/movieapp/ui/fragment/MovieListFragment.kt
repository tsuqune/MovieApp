package com.bignerdranch.android.movieapp.ui.fragment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bignerdranch.android.movieapp.ui.components.MovieItem
import com.bignerdranch.android.movieapp.ui.components.FilterDropdown
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieListFragment(
    navController: NavHostController,
    viewModel: MovieViewModel
) {
    val movies by viewModel.movies.collectAsState()
    val filterState by viewModel.filterState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column {
        SearchAndFilterBar(
            searchQuery = searchQuery,
            onSearchChange = { viewModel.searchAnime(it) },
            currentFilter = filterState,
            onFilterSelected = { type ->
                when(type) {
                    "top" -> viewModel.loadTopMovies()
                    "recent" -> viewModel.loadRecentAnime()
                }
                expanded = false
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        )

        if (movies.isEmpty() && searchQuery.isNotEmpty()) {
            Text("Ничего не найдено", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(movies) { movie ->
                    MovieItem(
                        movie = movie,
                        navController = navController // Передаем контроллер
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchAndFilterBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    currentFilter: String,
    onFilterSelected: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Row(modifier = Modifier.padding(16.dp)) {
        // Реализация SearchBar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(8.dp))

        // Реализация FilterDropdown
        FilterDropdown(
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            currentFilter = currentFilter,
            onFilterSelected = onFilterSelected
        )
    }
}