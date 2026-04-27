package com.practice.rickyandmorty.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Route: NavKey {
    @Serializable
    data object Splash: Route()
    @Serializable
    data object CharacterList: Route()
    @Serializable
    data class CharacterDetail(val id: Int?, val name: String?): Route()
    @Serializable
    data object SearchCharacter: Route()
    @Serializable
    data object Favorites: Route()
    @Serializable
    data object Profile: Route()
}

