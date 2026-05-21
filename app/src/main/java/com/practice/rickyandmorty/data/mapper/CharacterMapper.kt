package com.practice.rickyandmorty.data.mapper

import com.practice.rickyandmorty.data.remote.model.CharacterDto
import com.practice.rickyandmorty.database.entities.CharacterEntity
import com.practice.rickyandmorty.domain.model.Character

fun CharacterDto.toEntity(queryKey: String): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    episodes = episode?.map { "$it," }.toString(),
    url = url,
    created = created,
    queryKey = queryKey
)

fun CharacterEntity.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    episode = episodes?.split(","),
    url = url,
    created = created
)
