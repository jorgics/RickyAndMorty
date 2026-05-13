package com.practice.rickyandmorty.presentation.navigation

import androidx.navigation3.runtime.NavKey
import com.practice.rickyandmorty.domain.model.CharacterFilter
import kotlinx.serialization.Serializable

sealed class Route: NavKey {
    @Serializable
    data object Splash: Route()
    @Serializable
    data class CharacterList(val filter: CharacterFilter?): Route()
    @Serializable
    data class CharacterDetail(val id: Int?, val name: String?): Route()
    @Serializable
    data object SearchCharacter: Route()
    @Serializable
    data object Favorites: Route()
    @Serializable
    data object Profile: Route()
}

