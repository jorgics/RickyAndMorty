package com.practice.rickyandmorty.presentation.search

import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.CharacterFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() :
    BaseViewModel<SearchIntent, SearchState>(SearchState()) {
    override fun sendIntent(intent: SearchIntent) {
        handleIntents(intent)
    }

    override fun handleIntents(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ApplyFilter -> setState {
                copy(
                    filter = CharacterFilter(
                        name = name
                    ),
                    search = true
                )
            }

            is SearchIntent.SelectedName -> setState {
                copy(name = intent.name)
            }

            is SearchIntent.SearchClear -> setState {
                copy(search = false)
            }
        }
    }
}

data class SearchState(
    val name: String? = null,
    val search: Boolean = false,
    val filter: CharacterFilter? = null
)

sealed class SearchIntent {
    data object ApplyFilter : SearchIntent()
    data class SelectedName(val name: String?) : SearchIntent()
    data object SearchClear : SearchIntent()
}