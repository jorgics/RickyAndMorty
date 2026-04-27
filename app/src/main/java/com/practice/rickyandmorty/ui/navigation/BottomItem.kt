package com.practice.rickyandmorty.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.core.extensions.navigateTo

enum class BottomItem {
    EXPLORE, FAVORITES, PROFILE, SEARCH
}

fun getTitle(route: Route?): String {
    return when (route) {
        is Route.CharacterList -> "Explore"
        is Route.CharacterDetail -> route.name ?: "Character Detail"
        is Route.Favorites -> "Favorites"
        is Route.SearchCharacter -> "Search"
        is Route.Profile -> "Profile"
        else -> "Explore"
    }
}