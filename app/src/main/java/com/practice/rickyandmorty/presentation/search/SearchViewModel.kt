package com.practice.rickyandmorty.presentation.search

import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.Gender
import com.practice.rickyandmorty.domain.model.Status
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
                        name = name,
                        gender = gender?.lowercase(),
                        status = status?.lowercase()
                    ),
                    search = true
                )
            }

            is SearchIntent.SelectedName -> setState {
                copy(name = intent.name)
            }

            is SearchIntent.SearchClear -> setState {
                copy(
                    search = false,
                    name = null,
                    gender = null,
                    status = null
                )
            }

            is SearchIntent.SelectedGender -> setState {
                copy(gender = intent.gender)
            }

            is SearchIntent.SelectedStatus -> setState {
                copy(status = intent.status)
            }
        }
    }
}

data class SearchState(
    val name: String? = null,
    val gender: String? = null,
    val status: String? = null,
    val search: Boolean = false,
    val genders: List<String> = listOf(Gender.All.value, Gender.Male.value, Gender.Female.value, Gender.Genderless.value, Gender.Unknown.value),
    val statuses: List<String> = listOf(Status.All.value, Status.Alive.value, Status.Dead.value, Status.Unknown.value),
    val filter: CharacterFilter? = null
)

sealed class SearchIntent {
    data object ApplyFilter : SearchIntent()
    data class SelectedName(val name: String?) : SearchIntent()
    data class SelectedGender(val gender: String?) : SearchIntent()
    data class SelectedStatus(val status: String?) : SearchIntent()
    data object SearchClear : SearchIntent()
}