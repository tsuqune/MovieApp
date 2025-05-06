package com.bignerdranch.android.movieapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun FilterDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    currentFilter: String,
    onFilterSelected: (String) -> Unit
) {
    Box {
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
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

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