package com.practice.rickyandmorty.data.mapper

import com.practice.rickyandmorty.data.remote.model.CharacterDto
import com.practice.rickyandmorty.data.remote.model.LocationDto
import com.practice.rickyandmorty.data.remote.model.OriginDto
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.Location
import com.practice.rickyandmorty.domain.model.Origin

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    origin = origin?.toDomain(),
    location = location?.toDomain(),
    episode = episode,
    url = url,
    created = created
)

fun LocationDto.toDomain(): Location = Location(
    name = name,
    url = url
)

fun OriginDto.toDomain(): Origin = Origin(
    name = name,
    url = url
)
