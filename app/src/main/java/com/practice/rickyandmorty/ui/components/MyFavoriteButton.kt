package com.practice.rickyandmorty.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.practice.rickyandmorty.R

@Composable
fun MyFavoriteButton(
    isFavorite: Boolean = true,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = Color.Red
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(
                if (isFavorite)
                    R.drawable.baseline_favorite_24 else
                    R.drawable.favorite_24px
            ),
            contentDescription = if (isFavorite) "delete favorite" else "add favorite",
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}