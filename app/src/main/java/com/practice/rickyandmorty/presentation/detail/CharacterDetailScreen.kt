package com.practice.rickyandmorty.presentation.detail

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.ui.components.ExpandableContent
import com.practice.rickyandmorty.ui.components.MyDialog
import com.practice.rickyandmorty.ui.components.MyErrorDialog
import com.practice.rickyandmorty.ui.components.MyFavoriteButton
import com.practice.rickyandmorty.ui.components.MyHorizontalSpacer
import com.practice.rickyandmorty.ui.components.MyImage
import com.practice.rickyandmorty.ui.components.MyImageSource
import com.practice.rickyandmorty.ui.components.MyLoadingProgress
import com.practice.rickyandmorty.ui.components.TextValueColumn
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.ui.theme.CardBackground
import com.practice.rickyandmorty.ui.theme.GrayLight

@Composable
fun CharacterDetailScreen(
    id: Int,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CharacterDetailIntent.LoadCharacter(id))
    }

    CharacterDetailScreenContent(uiState = uiState, onIntent = { viewModel.sendIntent(it) })
}

@Composable
fun CharacterDetailScreenContent(
    uiState: CharacterDetailState,
    onIntent: (CharacterDetailIntent) -> Unit
) {
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (uiState.isDialog) {
                    MyDialog(
                        title = "Add to favorites",
                        description = "Do you want to add ${uiState.character?.name} to your favorites?",
                        onConfirm = { onIntent(CharacterDetailIntent.ToogleFavorite) },
                        onDismiss = { onIntent(CharacterDetailIntent.DismissFavoriteDialog) }
                    )
                }

                uiState.character?.let {
                    CharacterDetailItem(
                        character = uiState.character,
                        isFavorite = uiState.isFavorite,
                        onFavoriteClick = {
                            onIntent(CharacterDetailIntent.ShowFavoriteDialog)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterDetailItem(
    character: Character,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
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

        MyFavoriteButton(
            modifier = Modifier.align(Alignment.TopEnd),
            size = 32.dp,
            isFavorite = isFavorite,
            isDisable = isFavorite,
            onToggle = { onFavoriteClick() }
        )

        ExpandableContent(
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
                ),
            initialExpanded = true,
            size = 32.dp
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextValueColumn(
                modifier = Modifier.weight(1f),
                title = "STATUS",
                value = character.status
            )
            TextValueColumn(
                modifier = Modifier.weight(1f),
                title = "SPECIE",
                value = character.species
            )
        }

        MyHorizontalSpacer(Modifier.padding(vertical = 8.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextValueColumn(
                modifier = Modifier.weight(1f),
                title = "GENDER",
                value = character.gender
            )
            TextValueColumn(
                modifier = Modifier.weight(1f),
                title = "ORIGEN",
                value = character.origin
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

@Preview
@Composable
fun CharacterDetailScreenPreview() {
    val uiState = CharacterDetailState(
        character = Character(
            id = 1,
            name = "Rick Sanchez",
            species = "Human"
        ),
        isLoading = false
    )

    CharacterDetailScreenContent(uiState = uiState, onIntent = {})
}