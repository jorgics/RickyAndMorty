package com.practice.rickyandmorty.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.ui.MyErrorDialog
import com.practice.rickyandmorty.core.ui.MyHorizontalSpacer
import com.practice.rickyandmorty.core.ui.MyImage
import com.practice.rickyandmorty.core.ui.MyImageSource
import com.practice.rickyandmorty.core.ui.MyLoadingProgress
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.ui.theme.CardBackground
import com.practice.rickyandmorty.ui.theme.GrayLight
import com.practice.rickyandmorty.ui.theme.GrayMedium

@Composable
fun CharacterDetailScreen(
    id: Int?,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CharacterDetailIntent.LoadCharacter(id))
    }

    CharacterDetailScreenContent(uiState = uiState)
}

@Composable
fun CharacterDetailScreenContent(uiState: CharacterDetailState) {
    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MyLoadingProgress()
            }
        }

        uiState.error is BaseException -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MyErrorDialog(uiState.error) { }
            }
        }

        else -> {
            CharacterDetailItem(character = uiState.character)
        }
    }
}

@Composable
fun CharacterDetailItem(character: Character) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.BottomCenter
    ) {
        MyImage(
            source = MyImageSource.Url(url = character.image, resolution = 512),
            contentDescription = character.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 350.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            CharacterInfoGrid(character)
        }
    }
}

@Composable
fun CharacterInfoGrid(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CardBackground,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .padding(16.dp)
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(modifier = Modifier.weight(1f), title = "STATUS", value = character.status)
            InfoItem(modifier = Modifier.weight(1f), title = "SPECIE", value = character.species)
        }

        MyHorizontalSpacer(Modifier.padding(vertical = 8.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(modifier = Modifier.weight(1f), title = "GENDER", value = character.gender)
            InfoItem(
                modifier = Modifier.weight(1f),
                title = "ORIGEN",
                value = character.origin?.name
            )
        }

        MyHorizontalSpacer(Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "ABOUT ${character.name}",
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = character.episode?.size.toString() + " episodes",
            color = GrayLight
        )
    }
}

@Composable
fun InfoItem(
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

