package com.practice.rickyandmorty.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.ui.components.FilterLayout
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.ui.theme.GrayMedium
import com.practice.rickyandmorty.ui.theme.Green

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
        gender = uiState.gender,
        status = uiState.status,
        genders = uiState.genders,
        statuses = uiState.statuses,
        onIntent = { viewModel.sendIntent(it) }
    )
}

@Composable
fun SearchScreenContent(
    gender: String? = "ALL",
    status: String? = "ALL",
    genders: List<String>,
    statuses: List<String>,
    onIntent: (SearchIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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

            HeaderFilter(
                onCleanClick = { onIntent(SearchIntent.SearchClear) }
            )

            FilterLayout(
                title = "GENDER",
                selected = gender ?: "ALL",
                items = genders,
                onClick = { onIntent(SearchIntent.SelectedGender(it)) }
            )

            FilterLayout(
                title = "STATUS",
                selected = status ?: "ALL",
                items = statuses,
                onClick = { onIntent(SearchIntent.SelectedStatus(it)) }
            )
        }

        ActionsSearch(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
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
                Text("Search character name...")
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
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = GrayMedium,
                unfocusedBorderColor = GrayMedium,
                unfocusedLabelColor = GrayMedium,
                unfocusedTrailingIconColor = GrayMedium,
                unfocusedPlaceholderColor = GrayMedium,
                unfocusedLeadingIconColor = GrayMedium,
                focusedPlaceholderColor = Green,
                focusedLeadingIconColor = Green,
                focusedTrailingIconColor = Green,
                focusedLabelColor = Green,
                focusedTextColor = Green,
                focusedBorderColor = Green
            )
        )
    }
}

@Composable
fun HeaderFilter(
    onCleanClick: () -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "FILTERS",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp
        )

        Text(
            modifier = Modifier.clickable { onCleanClick() },
            text = "Clean all",
            color = Color.Green
        )
    }

}

@Composable
fun ActionsSearch(
    modifier: Modifier,
    onSearch: () -> Unit = { }
) {
    FloatingActionButton(
        modifier = modifier,
        shape = FloatingActionButtonDefaults.largeShape,
        onClick = { onSearch() },
        containerColor = Green,
        contentColor = Color.White
    ) {
        Icon(
            painter = painterResource(R.drawable.search_24px),
            contentDescription = "Search"
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    val fakeState = SearchState()
    SearchScreenContent(
        gender = fakeState.gender,
        genders = fakeState.genders,
        status = fakeState.status,
        statuses = fakeState.statuses,
        onIntent = { },
    )
}