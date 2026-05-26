package com.practice.rickyandmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practice.rickyandmorty.ui.theme.GrayMedium

@Preview
@Composable
fun MyHorizontalSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .size(2.dp)
        .background(GrayMedium)
    )
}

@Preview
@Composable
fun MyVerticalSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier
        .fillMaxHeight()
        .size(2.dp)
        .background(GrayMedium)
    )
}