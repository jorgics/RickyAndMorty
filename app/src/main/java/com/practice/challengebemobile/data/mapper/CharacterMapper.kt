package com.practice.challengebemobile.data.mapper

import com.practice.challengebemobile.data.remote.model.CharacterDto
import com.practice.challengebemobile.domain.model.Character

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image
)
