package com.practice.rickyandmorty.core.ui

import androidx.compose.runtime.Composable
import com.practice.rickyandmorty.core.data.exceptions.BaseException

@Composable
fun MyErrorDialog(
    error: BaseException,
    onDismiss: () -> Unit
) {

    val message = when (error) {
        is BaseException.Network -> "Error de conexión"
        is BaseException.Timeout -> "Timeout de red"
        is BaseException.HttpError -> "Error servidor (${error.code})"
        is BaseException.Unknown -> "Error desconocido"
    }

    MyDialog(
        title = "Error",
        description = message,
        onDismiss = onDismiss
    )
}