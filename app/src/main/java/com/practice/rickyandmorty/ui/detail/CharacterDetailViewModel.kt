package com.practice.rickyandmorty.ui.detail

import androidx.lifecycle.viewModelScope
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.usecase.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : BaseViewModel<CharacterDetailIntent, CharacterDetailState>(CharacterDetailState()) {

    private fun getCharacterById(id: Int?) {
        viewModelScope.launch {
            if (id == null) {
                setState { copy(isLoading = false, error = BaseException.NoData()) }
                return@launch
            }
            setState { copy(isLoading = true) }
            async {
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
                        setState { copy(error = response.exception) }
                    }
                }
            }.await()
        }
    }

    override fun sendIntent(intent: CharacterDetailIntent) {
        viewModelScope.launch {
            handleIntents(intent)
        }
    }

    override fun handleIntents(intent: CharacterDetailIntent) {
        viewModelScope.launch {
            when (intent) {
                is CharacterDetailIntent.LoadCharacter -> {
                    getCharacterById(intent.id)
                }
            }
        }
    }
}

data class CharacterDetailState (
    val isLoading: Boolean = true,
    val error: BaseException? = null,
    val character: Character = Character()
)

sealed class CharacterDetailIntent {
    data class LoadCharacter(val id: Int?) : CharacterDetailIntent()
}