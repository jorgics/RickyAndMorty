package com.practice.rickyandmorty.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<Intent, State>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected fun setState(reducer: State.() -> State) {
        _state.update(reducer)
    }

    abstract fun sendIntent(intent: Intent)

    protected abstract fun handleIntents(intent: Intent)
}