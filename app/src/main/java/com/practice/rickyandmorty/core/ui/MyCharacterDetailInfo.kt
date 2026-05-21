package com.practice.rickyandmorty.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.ui.theme.CardBackground
import com.practice.rickyandmorty.ui.theme.GrayLight

@Composable
fun MyCharacterDetailInfo(
    character: Character
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
            TextValueColumn(modifier = Modifier.weight(1f), title = "STATUS", value = character.status)
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
            TextValueColumn(modifier = Modifier.weight(1f), title = "GENDER", value = character.gender)
            TextValueColumn(
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