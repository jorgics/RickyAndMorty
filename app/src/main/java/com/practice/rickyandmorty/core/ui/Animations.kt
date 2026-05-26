package com.practice.rickyandmorty.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.practice.rickyandmorty.R

@Composable
fun ExpandableContent(
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(initialExpanded) }

    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "rotation"
    )
    val dragThreshold = 50f

    Column(
        modifier = modifier
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > dragThreshold) {
                        isExpanded = false
                    } else if (dragAmount < -dragThreshold) {
                        isExpanded = true
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { isExpanded = !isExpanded },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                modifier = Modifier
                    .rotate(rotation),
                painter = painterResource(R.drawable.keyboard_arrow_up_24px),
                contentDescription = null
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
        ) {
            content()
        }
    }
}

@Composable
fun FlipContent(
    modifier: Modifier = Modifier,
    initialFlipped: Boolean = false,
    durationMillis: Int = 600,
    easing: Easing = FastOutSlowInEasing,
    cameraDistanceFactor: Float = 12f,
    front: @Composable BoxScope.(flipAction: () -> Unit) -> Unit,
    back: @Composable BoxScope.(flipAction: () -> Unit) -> Unit
) {
    var flipped by remember { mutableStateOf(initialFlipped) }
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = durationMillis, easing = easing),
        label = "flip-rotation"
    )

    val density = LocalDensity.current
    val cameraDistancePx = with(density) { 48.dp.toPx() } * cameraDistanceFactor

    val flipAction = { flipped = !flipped }

    Box(
        modifier = modifier.graphicsLayer {
            rotationY = rotation
            cameraDistance = cameraDistancePx
        },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            Box(modifier = Modifier.fillMaxSize(), content = { front(flipAction) })
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f },
                content = { back(flipAction) }
            )
        }
    }
}
