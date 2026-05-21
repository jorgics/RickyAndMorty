package com.practice.rickyandmorty.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.practice.rickyandmorty.ui.theme.GrayMedium

@Composable
fun TextValueColumn(
    modifier: Modifier = Modifier,
    title: String,
    value: String?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title, color = GrayMedium, fontSize = 12.sp)
        Text(value ?: "Unknown", color = Color.White, fontWeight = FontWeight.Bold)
    }
}