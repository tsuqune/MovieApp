package com.bignerdranch.android.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bignerdranch.android.movieapp.ui.fragment.AnimeDetailFragment
import com.bignerdranch.android.movieapp.ui.fragment.MovieListFragment
import com.bignerdranch.android.movieapp.ui.fragment.WatchedAnimeFragment
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModel
import com.bignerdranch.android.movieapp.viewmodel.MovieViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MovieViewModel = remember {
                MovieViewModelFactory((application as MovieApp).database)
                    .create(MovieViewModel::class.java)
            }

            MaterialTheme {
                AppNavigation(navController, viewModel)
            }
        }
    }
}

@Composable
private fun AppNavigation(
    navController: NavHostController,
    viewModel: MovieViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == "main"
                    } == true,
                    onClick = { navController.navigate("main") },
                    icon = { Icon(Icons.Default.Home, "Главная") },
                    label = { Text("Главная") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == "watched"
                    } == true,
                    onClick = { navController.navigate("watched") },
                    icon = { Icon(Icons.Default.Visibility, "Просмотрено") },
                    label = { Text("Просмотрено") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(padding)
        ) {
            composable("main") {
                MovieListFragment(navController, viewModel)
            }
            composable("watched") {
                WatchedAnimeFragment(viewModel, navController)
            }
            composable("detail/{movieId}") { backStackEntry ->
                AnimeDetailFragment(
                    movieId = backStackEntry.arguments?.getString("movieId") ?: "",
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}