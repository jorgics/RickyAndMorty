package com.practice.rickyandmorty.presentation.favorites

import androidx.lifecycle.viewModelScope
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.Favorite
import com.practice.rickyandmorty.domain.usecase.DeleteFavoriteUseCase
import com.practice.rickyandmorty.domain.usecase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : BaseViewModel<FavoritesIntent, FavoritesState>(FavoritesState()) {

    init {
        sendIntent(FavoritesIntent.LoadFavorites)
    }

    override fun sendIntent(intent: FavoritesIntent) {
        handleIntents(intent)
    }

    override fun handleIntents(intent: FavoritesIntent) {
        when (intent) {
            FavoritesIntent.LoadFavorites -> loadFavorites()
            is FavoritesIntent.DeleteFavorite -> deleteFavorite(intent.id)
            is FavoritesIntent.ShowDeleteDialog -> setState {
                copy(
                    isDelete = !state.value.isDelete,
                    favorite = intent.favorite
                )
            }

            FavoritesIntent.DismissDeleteDialog -> dismissDeleteDialog()
        }
    }

    private fun loadFavorites() {
        getFavoritesUseCase.invoke()
            .onEach { list ->
                setState { copy(isLoading = false, favorites = list) }
            }
            .catch { _ ->
                setState{ copy(isLoading = false, error = BaseException.Unknown()) }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            deleteFavoriteUseCase.invoke(id)
            setState { copy(isDelete = false) }
        }
    }

    private fun dismissDeleteDialog() {
        setState { copy(isDelete = false) }
    }
}

data class FavoritesState(
    val favorites: List<Favorite> = emptyList(),
    val isLoading: Boolean = true,
    val error: BaseException? = null,
    val isDelete: Boolean = false,
    val favorite: Favorite? = null,
)

sealed class FavoritesIntent {
    data object LoadFavorites : FavoritesIntent()
    data object DismissDeleteDialog : FavoritesIntent()
    data class ShowDeleteDialog(val favorite: Favorite) : FavoritesIntent()
    data class DeleteFavorite(val id: Int) : FavoritesIntent()
}