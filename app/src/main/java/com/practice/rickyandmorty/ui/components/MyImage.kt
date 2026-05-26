package com.practice.rickyandmorty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.practice.rickyandmorty.R

sealed class MyImageSource {
    data class Url(val url: String?, val resolution: Int = 128) : MyImageSource()
    data class Resource(val resId: Int) : MyImageSource()
}

@Composable
fun MyImage(
    modifier: Modifier = Modifier,
    source: MyImageSource,
    contentDescription: String? = null,
    placeholder: Painter = painterResource(id = R.drawable.placeholder),
    error: Painter = painterResource(id = R.drawable.placeholder),
    contentScale: ContentScale = ContentScale.Crop
) {
    when (source) {
        is MyImageSource.Url -> AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(source.url)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .size(source.resolution)
                .crossfade(true)
                .build(),
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