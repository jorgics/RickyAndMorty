package com.practice.rickyandmorty.core.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.presentation.navigation.BottomItem

@Composable
fun MyBottomBar(
    selectedItem: BottomItem,
    onHomeClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Column {
        MyHorizontalSpacer()

        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) {
            MyBottomItem(
                modifier = Modifier.weight(1f),
                selected = selectedItem == BottomItem.EXPLORE,
                icon = R.drawable.home_24px,
                iconSelected = R.drawable.baseline_home_24,
                label = BottomItem.EXPLORE.name,
                onClick = onHomeClick
            )

            MyBottomItem(
                modifier = Modifier.weight(1f),
                selected = selectedItem == BottomItem.FAVORITES,
                icon = R.drawable.favorite_24px,
                iconSelected = R.drawable.baseline_favorite_24,
                label = BottomItem.FAVORITES.name,
                onClick = onFavoritesClick
            )

            MyBottomItem(
                modifier = Modifier.weight(1f),
                selected = selectedItem == BottomItem.SEARCH,
                icon = R.drawable.search_24px,
                label = BottomItem.SEARCH.name,
                onClick = onSearchClick
            )

            MyBottomItem(
                modifier = Modifier.weight(1f),
                selected = selectedItem == BottomItem.PROFILE,
                icon = R.drawable.person_24px,
                iconSelected = R.drawable.baseline_person_24,
                label = BottomItem.PROFILE.name,
                onClick = onProfileClick
            )
        }
    }
}

@Composable
fun MyBottomItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: Int,
    iconSelected: Int = icon,
    label: String,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = ""
    )

    Column(
        modifier = modifier
            .clickable(!selected) { onClick() }
            .padding(vertical = 8.dp)
            .scale(scale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(if (!selected) icon else iconSelected),
            contentDescription = null,
            tint = if (selected) Color.White else Color.White.copy(alpha = 0.6f)
        )

        Text(
            text = label,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.6f),
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}