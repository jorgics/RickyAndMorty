package com.practice.rickyandmorty.presentation.favorites

import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(

) : BaseViewModel<FavoritesIntent, FavoritesState>(FavoritesState()) {

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val favoritesFlow = state
        .map { it.characters }
        .distinctUntilChanged()
        .debounce(300)
        .flatMapLatest { characters ->
            flowOf(characters)
        }

    override fun sendIntent(intent: FavoritesIntent) {
        handleIntents(intent)
    }

    override fun handleIntents(intent: FavoritesIntent) {
        when (intent) {
            FavoritesIntent.EmptyCharacters -> setState {
                copy(characters = emptyList())
            }
            FavoritesIntent.LoadCharacters -> setState {
                copy(characters = characters)
            }

            is FavoritesIntent.DeleteFavorite -> setState {
                copy(characters = characters.drop(intent.id))
            }
        }
    }

}

data class FavoritesState(
    val characters: List<Character> = getList(),
    val currentCharacter: Character? = null,
    val isLoading: Boolean = true,
)

sealed class FavoritesIntent {
    data object LoadCharacters : FavoritesIntent()
    data object EmptyCharacters : FavoritesIntent()
    data class DeleteFavorite(val id: Int) : FavoritesIntent()
}

fun getList() = listOf(character, character2, character3)

val character = Character(
    id = 1,
    name = "Ricky",
    status = "Dead",
    gender = "Male",
    species = "Human",
    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
)

val character2 = Character(
    id = 2,
    name = "Joel",
    status = "Dead",
    gender = "Male",
    species = "Human",
    image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
)

val character3 = Character(
    id = 3,
    name = "Adam",
    status = "Dead",
    gender = "Male",
    species = "Human",
    image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
)