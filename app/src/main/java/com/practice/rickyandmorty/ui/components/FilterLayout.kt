package com.practice.rickyandmorty.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterLayout(
    title: String = "",
    selected: String,
    items: List<String>,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {

        Text(
            text = title,
            color = Color.White
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(
                count = items.size,
                key = { index -> index }
            ) { index ->
                MyFilterItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    selected = selected.equals(items[index], ignoreCase = true),
                    label = items[index].uppercase(),
                    onClick = { onClick(items[index]) }
                )
            }
        }
    }
}