package com.bignerdranch.android.movieapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bignerdranch.android.movieapp.model.Movie

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    builder = { crossfade(true) }
                ),
                contentDescription = movie.title,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "★ ${movie.vote_average}", color = Color(0xFFFFA500))
                Text(text = movie.overview, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}