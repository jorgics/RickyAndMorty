package com.practice.rickyandmorty.presentation.profile

import androidx.lifecycle.viewModelScope
import com.practice.rickyandmorty.core.ui.viewmodel.BaseViewModel
import com.practice.rickyandmorty.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :
    BaseViewModel<ProfileIntent, ProfileState>(ProfileState()) {

    override fun sendIntent(intent: ProfileIntent) {
        handleIntents(intent)
    }

    override fun handleIntents(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.Login -> setState {
                copy(
                    user = User(
                        id = "1",
                        name = name,
                        email = email),
                    isLogged = true
                )
            }

            ProfileIntent.Logout -> setState {
                copy(
                    user = null,
                    name = "",
                    email = "",
                    isLogged = false
                )
            }

            is ProfileIntent.OnEmailChange -> setState {
                copy(email = intent.email)
            }
            is ProfileIntent.OnNameChange -> setState {
                copy(name = intent.name)
            }
        }
    }

}

data class ProfileState(
    val user: User? = null,
    val name: String = "",
    val email: String = "",
    val isLogged: Boolean = false,
)

sealed class ProfileIntent {
    data class OnNameChange(val name: String) : ProfileIntent()
    data class OnEmailChange(val email: String) : ProfileIntent()
    data object Login : ProfileIntent()
    data object Logout : ProfileIntent()
}