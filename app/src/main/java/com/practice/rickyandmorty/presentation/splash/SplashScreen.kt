package com.practice.rickyandmorty.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.core.ui.MyImage
import com.practice.rickyandmorty.core.ui.MyImageSource
import com.practice.rickyandmorty.core.ui.MyLoadSplash
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(
    onNavigate: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.BottomCenter
    ) {
        MyImage(
            source = MyImageSource.Resource(R.drawable.splash),
            modifier = Modifier.fillMaxSize()
        )

        MyLoadSplash()
    }
}