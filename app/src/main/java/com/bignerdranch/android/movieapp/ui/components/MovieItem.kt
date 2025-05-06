package com.bignerdranch.android.movieapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bignerdranch.android.movieapp.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    navController: NavController,
    onClick: () -> Unit = { navController.navigate("detail/${movie.id}") }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = rememberImagePainter(data = movie.poster?.url),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(100.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = movie.name ?: "Без названия",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 4,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "★ ${movie.rating?.imdb?.formatRating() ?: "Н/Д"}",
                    color = Color(0xFFFFA500),
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.description ?: "Описание отсутствует",
                fontSize = 13.sp,
                lineHeight = 16.sp,
                maxLines = 5,
            )
        }
    }
}

fun Double.formatRating(): String {
    return if (this % 1 == 0.0) {
        this.toInt().toString()
    } else {
        "%.1f".format(this).replace(".0", "")
    }
}