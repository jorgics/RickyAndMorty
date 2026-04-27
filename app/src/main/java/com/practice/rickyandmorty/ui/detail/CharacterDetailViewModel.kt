package com.practice.rickyandmorty.ui.detail

import androidx.lifecycle.viewModelScope
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
) : BaseViewModel<CharacterDetailIntent, CharacterDetailState>(CharacterDetailState.Loading) {

    private fun getCharacterById(id: Int?) {
        viewModelScope.launch {
            if (id == null) {
                setState { CharacterDetailState.Error("Invalid character ID") }
                return@launch
            }
            setState { CharacterDetailState.Loading }
            async {
                when (val response = getCharacterByIdUseCase(id)) {
                    is BaseResponse.Success -> {
                        response.data.let { character ->
                            if (character != null) {
                                CharacterDetailState.Success(character)
                            } else {
                                CharacterDetailState.Error("Character not found")
                            }
                        }
                    }

                    is BaseResponse.Error -> {
                        CharacterDetailState.Error(
                            response.exception.message ?: "An error occurred"
                        )
                    }
                }
            }.await().let { state ->
                setState { state }
            }
        }
    }

    override fun sendIntent(intent: CharacterDetailIntent) {
        viewModelScope.launch {
            when (intent) {
                is CharacterDetailIntent.LoadCharacter -> {
                    getCharacterById(intent.id)
                }
            }
        }
    }
}

sealed class CharacterDetailState {
    data class Success(val character: Character) : CharacterDetailState()
    data class Error(val message: String) : CharacterDetailState()
    object Loading : CharacterDetailState()
}

sealed class CharacterDetailIntent {
    data class LoadCharacter(val id: Int?) : CharacterDetailIntent()
}