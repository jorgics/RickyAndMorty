package com.practice.rickyandmorty.ui.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.core.ui.MyImage
import com.practice.rickyandmorty.core.ui.MyImageSource
import com.practice.rickyandmorty.core.ui.MyLoadingProgress
import com.practice.rickyandmorty.domain.model.Character

@Composable
fun CharacterListScreen(
    onDetailClick: (Int?, String?) -> Unit = { _, _ -> },
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val characters = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    CharacterListScreenContent(
        uiState = uiState,
        characters = characters,
        onRefresh = { viewModel.sendIntent(CharacterListIntent.Retry) },
        onDetailClick = onDetailClick
    )
}

@Composable
fun CharacterListScreenContent(
    uiState: CharacterListState,
    characters: LazyPagingItems<Character>,
    onDetailClick: (Int?, String?) -> Unit,
    onRefresh: () -> Unit
) {
    if (uiState.isLoading && characters.loadState.refresh is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            MyLoadingProgress()
        }
    } else if (uiState.error != null && characters.loadState.refresh is LoadState.Error) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error loading characters")
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                count = characters.itemCount,
                key = { index -> characters[index]?.id ?: index }
            ) { index ->
                characters[index]?.let {
                    CharacterItem(character = it, onClick = onDetailClick)
                }
            }

            when (characters.loadState.append) {
                is LoadState.Loading -> item { LoadingEvent() }
                is LoadState.Error -> item { ErrorEvent { onRefresh() } }
                else -> Unit
            }

            when (characters.loadState.refresh) {
                is LoadState.Loading -> item { LoadingEvent() }
                is LoadState.Error -> item { ErrorEvent { onRefresh() } }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults
            .cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                    modifier = Modifier.size(128.dp),
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
fun ErrorEvent(onRefresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { onRefresh() }) { Text(text = "Retry") }
    }
}
