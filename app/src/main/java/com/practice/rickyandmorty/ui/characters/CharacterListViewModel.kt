package com.practice.rickyandmorty.ui.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : BaseViewModel<CharacterListIntent, CharacterListState>(CharacterListState()) {

    @OptIn(FlowPreview::class)
    private val filterFlow = state
        .map { it.filter }
        .distinctUntilChanged()
        .debounce(300)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow = filterFlow
        .flatMapLatest { filter ->
            getAllCharactersUseCase(filter)
        }
        .cachedIn(viewModelScope)

    override fun sendIntent(intent: CharacterListIntent) {
        handleIntents(intent)
    }

    override fun handleIntents(intent: CharacterListIntent) {
        when (intent) {
            is CharacterListIntent.ApplyFilter -> {
                setState {
                    copy(filter = intent.filter)
                }
            }

            is CharacterListIntent.Retry -> {
                retry()
            }
        }
    }

    private fun retry() {
        setState { copy(retry = !this.retry) }
    }
}

data class CharacterListState(
    val retry: Boolean = false,
    val filter: CharacterFilter = CharacterFilter()
)

sealed class CharacterListIntent {
    data class ApplyFilter(val filter: CharacterFilter) : CharacterListIntent()
    data object Retry : CharacterListIntent()
}
