package com.practice.rickyandmorty.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.practice.rickyandmorty.R
import com.practice.rickyandmorty.ui.components.MyImage
import com.practice.rickyandmorty.ui.components.MyImageSource
import com.practice.rickyandmorty.ui.theme.CardBackground
import com.practice.rickyandmorty.ui.theme.GrayMedium
import com.practice.rickyandmorty.ui.theme.Green

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    ProfileScreenContent(
        uiState = uiState,
        onIntent = { viewModel.sendIntent(it) }
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileState,
    onIntent: (ProfileIntent) -> Unit
) {
    if (uiState.isLogged) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderProfile(
                modifier = Modifier.padding(32.dp),
                name = uiState.name,
                email = uiState.email
            )
            DataProfile()
            Spacer(modifier = Modifier.size(8.dp))
            OptionsProfile(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onLogout = { onIntent(ProfileIntent.Logout) }
            )
        }
    } else {
        ProfileLoggedOut(
            onNameChange = { onIntent(ProfileIntent.OnNameChange(name = it)) },
            onEmailChange = { onIntent(ProfileIntent.OnEmailChange(email = it)) },
            onLoginClick = { onIntent(ProfileIntent.Login) }
        )
    }
}

@Composable
fun ProfileLoggedOut(
    onNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            value = name,
            onValueChange = { newName ->
                name = newName
                onNameChange(newName)
            },
            label = { Text("Complete name") },
            singleLine = true,
            trailingIcon = {
                if (name.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Clean",
                        modifier = Modifier.clickable {
                            name = ""
                            onNameChange("")
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = GrayMedium,
                unfocusedBorderColor = GrayMedium,
                unfocusedLabelColor = GrayMedium,
                unfocusedTrailingIconColor = GrayMedium,
                focusedLabelColor = Green,
                focusedTextColor = Green,
                focusedBorderColor = Green,
                focusedTrailingIconColor = Green
            )
        )

        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            value = email,
            onValueChange = { newEmail ->
                email = newEmail
                onEmailChange(newEmail)
            },
            label = { Text("Email") },
            singleLine = true,
            trailingIcon = {
                if (email.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Clean",
                        modifier = Modifier.clickable {
                            email = ""
                            onEmailChange("")
                        }
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = { onLoginClick() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = GrayMedium,
                unfocusedBorderColor = GrayMedium,
                unfocusedLabelColor = GrayMedium,
                unfocusedTrailingIconColor = GrayMedium,
                errorBorderColor = Color.Red,
                errorTextColor = Color.Red,
                errorTrailingIconColor = Color.Red,
                focusedLabelColor = Green,
                focusedTextColor = Green,
                focusedBorderColor = Green,
                focusedTrailingIconColor = Green
            )
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedButton(
            onClick = { onLoginClick() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                containerColor = Green
            )
        ) {
            Text(modifier = Modifier.padding(4.dp), text = "Login", fontSize = 18.sp)
        }
    }
}

@Composable
fun HeaderProfile(
    modifier: Modifier = Modifier,
    name: String = "Rick C-137",
    email: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        MyImage(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(50)
                )
                .size(128.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.Transparent),
            MyImageSource.Resource(R.drawable.person_24px)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = email, color = Color.White)
        }
    }
}

@Composable
fun DataProfile() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DataItem(
            name = "Favorites",
            total = "12"
        )

        DataItem(
            name = "Episodes",
            total = "24"
        )
    }
}

@Composable
fun DataItem(
    name: String,
    total: String
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = total, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = name, color = GrayMedium)
    }
}

@Composable
fun OptionsProfile(modifier: Modifier = Modifier, onLogout: () -> Unit) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .background(CardBackground, shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OptionItem(icon = R.drawable.favorite_24px, "Favorites")
        OptionItem(icon = R.drawable.video_library_24px, "Episodes")
        OptionItem(icon = R.drawable.settings_24px, "Settings")
        OptionItem(
            icon = R.drawable.logout_24px,
            "Logout",
            color = Color.Red,
            isAction = false,
            onClick = onLogout
        )
    }
}

@Composable
fun OptionItem(
    icon: Int,
    name: String,
    color: Color = Color.White,
    isAction: Boolean = true,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = color
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            text = name,
            color = color,
            fontSize = 16.sp
        )

        if (isAction) {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    val uiState = ProfileState()
    ProfileScreenContent(uiState = uiState, onIntent = {})
}