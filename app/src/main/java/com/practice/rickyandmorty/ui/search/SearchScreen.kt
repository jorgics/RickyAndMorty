package com.practice.rickyandmorty.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.domain.model.CharacterFilter

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onSearch: (CharacterFilter?) -> Unit = {}
) {
    val uiState = viewModel.state.collectAsState().value

    LaunchedEffect(uiState.search) {
        if (uiState.search) {
            onSearch(uiState.filter)
            viewModel.sendIntent(SearchIntent.SearchClear)
        }
    }

    SearchScreenContent(
        onIntent = { viewModel.sendIntent(it) }
    )
}

@Composable
fun SearchScreenContent(
    onIntent: (SearchIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterNameLayout(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                onIntent(
                    SearchIntent.SelectedName(name = it)
                )
            },
            onSearch = { onIntent(SearchIntent.ApplyFilter) }
        )
    }
}

@Composable
fun FilterNameLayout(
    modifier: Modifier = Modifier,
    onValueChange: (String?) -> Unit,
    onSearch: () -> Unit
) {
    var value by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                value = newValue
                onValueChange(newValue.ifEmpty { null })
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = {
                Text("Search character...")
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_24px),
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Clean",
                        modifier = Modifier.clickable {
                            value = ""
                            onValueChange(null)
                        }
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        onIntent = { }
    )
}