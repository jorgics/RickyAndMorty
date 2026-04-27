package com.practice.rickyandmorty.core.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.ui.navigation.BottomItem

@Composable
fun MyBottomBar(
    selectedItem: BottomItem,
    onHomeClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    NavigationBar(
        containerColor = Color.Black
    ) {
        NavigationBarItem(
            selected = selectedItem == BottomItem.EXPLORE,
            onClick = onHomeClick,
            icon = { Icon(painter = painterResource(R.drawable.home_24px), null) }
        )

        NavigationBarItem(
            selected = selectedItem == BottomItem.FAVORITES,
            onClick = onFavoritesClick,
            icon = { Icon(painter = painterResource(R.drawable.favorite_24px), null) }
        )

        NavigationBarItem(
            selected = selectedItem == BottomItem.SEARCH,
            onClick = onSearchClick,
            icon = { Icon(painter = painterResource(R.drawable.search_24px), null) }
        )

        NavigationBarItem(
            selected = selectedItem == BottomItem.PROFILE,
            onClick = onProfileClick,
            icon = { Icon(painter = painterResource(R.drawable.person_24px), null) }
        )
    }
}