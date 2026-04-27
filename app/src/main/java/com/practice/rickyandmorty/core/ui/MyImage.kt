package com.practice.rickyandmorty.core.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.practice.rickyandmorty.R

sealed class MyImageSource {
    data class Url(val url: String?) : MyImageSource()
    data class Resource(val resId: Int) : MyImageSource()
}

@Composable
fun MyImage(
    source: MyImageSource,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(id = R.drawable.placeholder),
    error: Painter = painterResource(id = R.drawable.placeholder),
    contentScale: ContentScale = ContentScale.Crop
) {
    when (source) {
        is MyImageSource.Url -> AsyncImage(
            model = source.url,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            placeholder = placeholder,
            error = error
        )
        is MyImageSource.Resource -> Image(
            painter = painterResource(id = source.resId),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}