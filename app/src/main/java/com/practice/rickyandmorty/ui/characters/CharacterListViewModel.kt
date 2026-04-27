package com.practice.rickyandmorty.ui.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : BaseViewModel<CharacterListIntent, CharacterListState>(CharacterListState()) {
    val intentChannel = Channel<CharacterListIntent>(Channel.UNLIMITED)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow = state
        .map { it.filter }
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            getAllCharactersUseCase(filter)
        }
        .cachedIn(viewModelScope)

    init {
        handleIntents()
        sendIntent(CharacterListIntent.LoadCharacters)
    }

    override fun sendIntent(intent: CharacterListIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is CharacterListIntent.LoadCharacters -> {
                        loadCharacters()
                    }

                    is CharacterListIntent.ApplyFilter -> {
                        setState{ copy(isLoading = false, filter = intent.filter) }
                    }

                    is CharacterListIntent.Retry -> {
                        loadCharacters()
                    }
                }
            }
        }
    }

    private fun loadCharacters() {
        setState { copy(isLoading = true, error = null) }
    }
}

data class CharacterListState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val filter: CharacterFilter = CharacterFilter()
)

sealed class CharacterListIntent {
    data object LoadCharacters : CharacterListIntent()
    data class ApplyFilter(val filter: CharacterFilter) : CharacterListIntent()
    data object Retry : CharacterListIntent()
}
