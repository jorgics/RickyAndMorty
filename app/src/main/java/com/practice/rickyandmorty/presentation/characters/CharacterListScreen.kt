package com.practice.rickyandmorty.presentation.characters

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.ui.MyErrorDialog
import com.practice.rickyandmorty.core.ui.MyImage
import com.practice.rickyandmorty.core.ui.MyImageSource
import com.practice.rickyandmorty.core.ui.MyLoadingProgress
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.ui.theme.BackgroundBrush
import kotlinx.coroutines.flow.flowOf

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onDetailClick: (Int?, String?) -> Unit = { _, _ -> },
    filter: CharacterFilter? = null
) {
    val uiState by viewModel.state.collectAsState()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        filter?.let {
            viewModel.sendIntent(CharacterListIntent.ApplyFilter(filter))
        }
    }

    CharacterListScreenContent(
        uiState = uiState,
        pagingItems = pagingItems,
        onIntent = { viewModel.sendIntent(it) },
        onDetailClick = onDetailClick
    )
}

@Composable
fun CharacterListScreenContent(
    uiState: CharacterListState,
    pagingItems: LazyPagingItems<Character>,
    onIntent: (CharacterListIntent) -> Unit,
    onDetailClick: (Int?, String?) -> Unit
) {
    LaunchedEffect(uiState.retry) {
        pagingItems.retry()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CharacterListEvent(
            pagingItems = pagingItems,
            onRetry = { onIntent(CharacterListIntent.Retry) },
            onDetailClick = onDetailClick
        )

        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> LoadingEvent()
            is LoadState.NotLoading -> if (pagingItems.itemCount == 0) {
                EmptyListEvent()
            }

            is LoadState.Error -> if (pagingItems.itemCount == 0) {
                ErrorEvent { onIntent(CharacterListIntent.Retry) }
            }
        }
    }
}

@Composable
fun CharacterListEvent(
    pagingItems: LazyPagingItems<Character>,
    onRetry: () -> Unit = {},
    onDetailClick: (Int?, String?) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBrush)
    ) {
        items(
            count = pagingItems.itemCount,
            key = { index -> pagingItems[index]?.id ?: index }
        ) { index ->
            pagingItems[index]?.let {
                CharacterItem(character = it, onClick = onDetailClick)
            }
        }

        item {
            when (pagingItems.loadState.append) {
                is LoadState.Loading -> LoadingEvent()
                is LoadState.Error -> ErrorEvent { onRetry() }
                else -> Unit
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character, onClick: (Int?, String?) -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick(character.id, character.name) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyImage(
                    source = MyImageSource.Url(character.image),
                    contentDescription = character.name,
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = character.name ?: "Unknown",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = character.species ?: "Unknown", color = Color.Green)
                    Text(text = character.gender ?: "Unknown")
                    Text(text = character.status ?: "Unknown")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier.weight(0.3f),
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Go to details"
                )
            }
        }
    }
}

@Composable
fun LoadingEvent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        MyLoadingProgress()
    }
}

@Composable
fun EmptyListEvent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Empty characters")
    }
}

@Composable
fun ErrorEvent(onRefresh: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyErrorDialog(BaseException.Unknown()) { onRefresh() }
    }
}


@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CharacterListScreenPreview() {
    val fakeCharacters = listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            species = "Human"
        ),
        Character(id = 2, name = "Morty Smith", species = "Human")
    )

    val pagingItems = flowOf(
        PagingData.from(fakeCharacters)
    ).collectAsLazyPagingItems()

    CharacterListScreenContent(
        uiState = CharacterListState(filter = CharacterFilter()),
        pagingItems = pagingItems,
        onIntent = {},
        onDetailClick = { _, _ -> }
    )
}
