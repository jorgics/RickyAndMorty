package com.practice.rickyandmorty.presentation.navigation

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