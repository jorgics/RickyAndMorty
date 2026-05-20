package com.practice.rickyandmorty.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.core.ui.FlipContent
import com.practice.rickyandmorty.core.ui.MyCharacterDetailInfo
import com.practice.rickyandmorty.core.ui.MyImage
import com.practice.rickyandmorty.core.ui.MyImageSource
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.ui.theme.BackgroundBrush
import com.practice.rickyandmorty.ui.theme.CardBackground

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value
    val favorites = viewModel.favoritesFlow.collectAsState(uiState.characters).value

    FavoritesContent(
        uiState = uiState,
        favorites = favorites,
        onIntent = { viewModel.sendIntent(it) }
    )
}

@Composable
fun FavoritesContent(
    uiState: FavoritesState,
    favorites: List<Character>,
    onIntent: (FavoritesIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        FavoritesCharacters(
            characters = favorites,
            { onIntent(FavoritesIntent.DeleteFavorite(it)) })
    }
}

@Composable
fun FavoritesCharacters(
    characters: List<Character?>,
    onFavoriteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBrush),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            count = characters.size,
            key = { index -> characters[index]?.id ?: index }
        ) { index ->
            characters[index]?.let {
                FavoriteFlipCard(character = it, onFavoriteClick = { onFavoriteClick(it.id!!) })
            }
        }
    }
}

@Composable
fun FavoriteFlipCard(character: Character, onFavoriteClick: () -> Unit) {
    FlipContent(
        front = { flip ->
            FavoriteCharacterCard(
                character = character,
                onClick = { flip() },
                onFavoriteClick = onFavoriteClick
            )
        },
        back = { flip ->
            FavoriteCharacterDetailCard (
                character = character,
                onClick = { flip() }
            )
        }
    )
}

@Composable
fun FavoriteCharacterCard(
    character: Character,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackground),
            contentAlignment = Alignment.Center
        ) {
            MyImage(
                modifier = Modifier.fillMaxSize(),
                source = MyImageSource.Url(character.image),
                contentDescription = character.name,
                contentScale = ContentScale.Crop
            )

            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { onFavoriteClick() },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Red,
                    containerColor = Color.Transparent
                )
            ) {
                Icon(painter = painterResource(R.drawable.favorite_24px), contentDescription = null)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(CardBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = character.name ?: "",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun FavoriteCharacterDetailCard(
    character: Character,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        MyCharacterDetailInfo(
            character = character
        )
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    val character = Character(
        id = 1,
        name = "Ricky",
        status = "Dead",
        gender = "Male",
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )

    val characters = listOf(character, character, character)
    val uiState = FavoritesState(characters = characters)

    FavoritesContent(uiState = uiState, favorites = characters, onIntent = {},)
}