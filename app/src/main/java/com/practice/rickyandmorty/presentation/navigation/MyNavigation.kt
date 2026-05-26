package com.practice.rickyandmorty.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.practice.rickyandmorty.core.extensions.back
import com.practice.rickyandmorty.core.extensions.navigateTo
import com.practice.rickyandmorty.core.extensions.navigateToSingleTop
import com.practice.rickyandmorty.ui.components.MyBottomBar
import com.practice.rickyandmorty.ui.components.MyScaffold
import com.practice.rickyandmorty.presentation.detail.CharacterDetailScreen
import com.practice.rickyandmorty.presentation.characters.CharacterListScreen
import com.practice.rickyandmorty.presentation.favorites.FavoritesScreen
import com.practice.rickyandmorty.presentation.profile.ProfileScreen
import com.practice.rickyandmorty.presentation.search.SearchScreen
import com.practice.rickyandmorty.presentation.splash.SplashScreen

@Composable
fun MyNavigation() {
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(Route.Splash)
    val currentRoute = backStack.lastOrNull()
    val showScaffold = currentRoute !is Route.Splash
    val selectedItem = when (currentRoute) {
        is Route.CharacterList -> BottomItem.EXPLORE
        is Route.CharacterDetail -> BottomItem.EXPLORE
        is Route.Favorites -> BottomItem.FAVORITES
        is Route.SearchCharacter -> BottomItem.SEARCH
        is Route.Profile -> BottomItem.PROFILE
        else -> BottomItem.EXPLORE
    }

    if (showScaffold) {
        MyScaffold(
            title = getTitle(currentRoute as Route?),
            showBackButton = currentRoute !is Route.CharacterList,
            onBackPressed = { backStack.back() },
            bottomBar = {
                MyBottomBar(
                    selectedItem = selectedItem,
                    onHomeClick = { backStack.navigateTo(Route.CharacterList(null)) },
                    onFavoritesClick = { backStack.navigateTo(Route.Favorites) },
                    onSearchClick = { backStack.navigateTo(Route.SearchCharacter) },
                    onProfileClick = { backStack.navigateTo(Route.Profile) }
                )
            }
        ) { innerPadding ->
            NavContent(backStack, innerPadding)
        }
    } else {
        NavContent(backStack)
    }
}

@Composable
fun NavContent(
    backStack: NavBackStack<NavKey>,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    NavDisplay(
        modifier = Modifier.padding(innerPadding),
        backStack = backStack,
        onBack = { backStack.back() },
        transitionSpec = {
            slideInHorizontally { it } togetherWith
                    slideOutHorizontally { -it }
        },
        popTransitionSpec = {
            slideInHorizontally { -it } togetherWith
                    slideOutHorizontally { it }
        },
        entryProvider = entryProvider {
            entry<Route.Splash> {
                SplashScreen(onNavigate = { backStack.navigateToSingleTop(Route.CharacterList(null)) })
            }

            entry<Route.CharacterList> { key ->
                CharacterListScreen(
                    onDetailClick = { id, name ->
                        backStack.navigateTo(Route.CharacterDetail(id, name))
                    },
                    filter = key.filter
                )
            }

            entry<Route.CharacterDetail> { key ->
                CharacterDetailScreen(id = key.id)
            }

            entry<Route.Favorites> {
                FavoritesScreen()
            }

            entry<Route.SearchCharacter> {
                SearchScreen { filter ->
                    backStack.navigateTo(Route.CharacterList(filter))
                }
            }

            entry<Route.Profile> {
                ProfileScreen()
            }
        }

    )
}