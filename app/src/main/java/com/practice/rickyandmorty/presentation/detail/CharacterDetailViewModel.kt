package com.practice.rickyandmorty.presentation.detail

import androidx.lifecycle.viewModelScope
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.core.presentation.viewmodel.BaseViewModel
import com.practice.rickyandmorty.data.mapper.toTransformFavorite
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.Favorite
import com.practice.rickyandmorty.domain.usecase.GetCharacterByIdUseCase
import com.practice.rickyandmorty.domain.usecase.GetIsFavoriteUseCase
import com.practice.rickyandmorty.domain.usecase.InsertFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getIsFavoriteUseCase: GetIsFavoriteUseCase
) : BaseViewModel<CharacterDetailIntent, CharacterDetailState>(CharacterDetailState()) {

    override fun sendIntent(intent: CharacterDetailIntent) {
        handleIntents(intent)
    }

    override fun handleIntents(intent: CharacterDetailIntent) {
        when (intent) {
            is CharacterDetailIntent.LoadCharacter -> {
                getIsFavorite(intent.id)
                getCharacterById(intent.id)
            }

            CharacterDetailIntent.ToogleFavorite -> {
                state.value.character?.let {
                    insertFavorite(it.toTransformFavorite())
                }
            }

            CharacterDetailIntent.DismissFavoriteDialog -> dismissDialog()
            CharacterDetailIntent.ShowFavoriteDialog -> showDialog()
        }
    }

    private fun getCharacterById(id: Int) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            when (val response = getCharacterByIdUseCase(id)) {
                is BaseResponse.Success -> {
                    response.data.let { character ->
                        if (character != null) {
                            setState { copy(isLoading = false, character = character) }
                        } else {
                            setState { copy(isLoading = false, error = BaseException.NoContent()) }
                        }
                    }
                }

                is BaseResponse.Error -> {
                    setState { copy(isLoading = false, error = response.exception) }
                }
            }
        }
    }

    private fun getIsFavorite(id: Int) {
        getIsFavoriteUseCase.invoke(id)
            .onEach { isFavorite ->
                setState {
                    copy(isFavorite = isFavorite)
                }
            }
            .catch { _ ->
                setState {
                    copy(error = BaseException.Unknown())
                }
            }
            .launchIn(viewModelScope)
    }

    private fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            insertFavoriteUseCase.invoke(favorite)
        }
    }

    private fun dismissDialog() {
        setState { copy(isDialog = false) }
    }

    private fun showDialog() {
        setState { copy(isDialog = true) }
    }
}

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val error: BaseException? = null,
    val isFavorite: Boolean = false,
    val character: Character? = null,
    val isDialog: Boolean = false
)

sealed class CharacterDetailIntent {
    data class LoadCharacter(val id: Int) : CharacterDetailIntent()
    data object ShowFavoriteDialog : CharacterDetailIntent()
    data object DismissFavoriteDialog : CharacterDetailIntent()
    data object ToogleFavorite : CharacterDetailIntent()
}