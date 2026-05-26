package com.practice.rickyandmorty.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.ui.FlipContent
import com.practice.rickyandmorty.core.ui.MyDialog
import com.practice.rickyandmorty.core.ui.MyErrorDialog
import com.practice.rickyandmorty.core.ui.MyFavoriteButton
import com.practice.rickyandmorty.core.ui.MyHorizontalSpacer
import com.practice.rickyandmorty.core.ui.MyImage
import com.practice.rickyandmorty.core.ui.MyImageSource
import com.practice.rickyandmorty.core.ui.MyLoadingProgress
import com.practice.rickyandmorty.core.ui.TextValueColumn
import com.practice.rickyandmorty.domain.model.Favorite
import com.practice.rickyandmorty.ui.theme.BackgroundBrush
import com.practice.rickyandmorty.ui.theme.CardBackground
import com.practice.rickyandmorty.ui.theme.GrayLight

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    FavoritesContent(
        uiState = uiState,
        onIntent = { viewModel.sendIntent(it) }
    )
}

@Composable
fun FavoritesContent(
    uiState: FavoritesState,
    onIntent: (FavoritesIntent) -> Unit
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
                FavoritesCharacters(
                    favorites = uiState.favorites,
                    onFavoriteClick = { onIntent(FavoritesIntent.ShowDeleteDialog(it)) }
                )

                if (uiState.isDelete) {
                    uiState.favorite?.let {
                        MyDialog(
                            title = "Delete ${it.name}",
                            description = "Are you sure you want to delete this favorite?",
                            onConfirm = { onIntent(FavoritesIntent.DeleteFavorite(it.id)) },
                            onDismiss = { onIntent(FavoritesIntent.DismissDeleteDialog) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoritesCharacters(
    favorites: List<Favorite?>,
    onFavoriteClick: (Favorite) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBrush),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            count = favorites.size,
            key = { index -> favorites[index]?.id ?: index }
        ) { index ->
            favorites[index]?.let { favorite ->
                FavoriteFlipCard(
                    favorite = favorite,
                    onFavoriteClick = { onFavoriteClick(favorite) }
                )
            }
        }
    }
}

@Composable
fun FavoriteFlipCard(
    favorite: Favorite,
    onFavoriteClick: (Favorite) -> Unit
) {
    FlipContent(
        front = { flip ->
            FavoriteCharacterCard(
                favorite = favorite,
                onClick = { flip() },
                onFavoriteClick = { onFavoriteClick(it) }
            )
        },
        back = { flip ->
            FavoriteCharacterDetailCard(
                favorite = favorite,
                onClick = { flip() }
            )
        }
    )
}

@Composable
fun FavoriteCharacterCard(
    favorite: Favorite,
    onClick: () -> Unit,
    onFavoriteClick: (Favorite) -> Unit
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
                source = MyImageSource.Url(favorite.image),
                contentDescription = favorite.name,
                contentScale = ContentScale.Crop
            )

            MyFavoriteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onToggle = { onFavoriteClick(favorite) },
                size = 32.dp,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(CardBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = favorite.name ?: "",
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
    favorite: Favorite,
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
            favorite = favorite
        )
    }
}

@Composable
fun MyCharacterDetailInfo(
    favorite: Favorite
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                value = favorite.status
            )
            TextValueColumn(
                modifier = Modifier.weight(1f),
                title = "SPECIE",
                value = favorite.species
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
                value = favorite.gender
            )
            TextValueColumn(
                modifier = Modifier.weight(1f),
                title = "ORIGEN",
                value = favorite.origin
            )
        }

        MyHorizontalSpacer(Modifier.padding(vertical = 8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "ABOUT ${favorite.name}",
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = favorite.episode?.size.toString() + " episodes",
            color = GrayLight
        )
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    val favorite = Favorite(
        id = 1,
        name = "Ricky",
        status = "Dead",
        gender = "Male",
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )

    val favorites = listOf(favorite, favorite, favorite)
    val uiState = FavoritesState(favorites = favorites)

    FavoritesContent(uiState = uiState, onIntent = {})
}