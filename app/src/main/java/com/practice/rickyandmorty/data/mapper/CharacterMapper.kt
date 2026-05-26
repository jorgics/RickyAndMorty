package com.practice.rickyandmorty.data.mapper

import com.practice.rickyandmorty.core.extensions.toEpisodes
import com.practice.rickyandmorty.data.remote.model.CharacterDto
import com.practice.rickyandmorty.database.entities.CharacterEntity
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.Favorite

fun CharacterDto.toEntity(queryKey: String): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    episodes = episode?.toString(),
    location = location?.name,
    origin = origin?.name,
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
    episode = episodes.toEpisodes(),
    origin = origin,
    url = url,
    created = created
)

fun Character.toTransformFavorite() = Favorite(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    episode = episode,
    origin = origin,
    url = url,
    created = created
)
