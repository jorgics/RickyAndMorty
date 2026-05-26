package com.practice.rickyandmorty.ui.components

import androidx.compose.runtime.Composable
import com.practice.rickyandmorty.core.data.exceptions.BaseException

@Composable
fun MyErrorDialog(
    error: BaseException,
    onDismiss: () -> Unit
) {

    val message = when (error) {
        is BaseException.Network -> "Error network"
        is BaseException.Timeout -> "Timeout de red"
        is BaseException.HttpError -> "Error server (${error.code})"
        is BaseException.Unknown -> "Error unknown"
        is BaseException.NoContent -> "No content"
        is BaseException.NoData -> "No data for request"
    }

    MyDialog(
        title = "Error",
        description = message,
        onDismiss = onDismiss
    )
}