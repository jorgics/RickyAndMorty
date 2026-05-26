package com.practice.rickyandmorty.data.mapper

import com.practice.rickyandmorty.core.extensions.toEpisodes
import com.practice.rickyandmorty.database.entities.FavoriteEntity
import com.practice.rickyandmorty.domain.model.Favorite

fun FavoriteEntity.toDomain() = Favorite(
    id = characterId,
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

fun Favorite.toEntity() = FavoriteEntity(
    characterId = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    episodes = episode.toString(),
    origin = origin,
    url = url,
    created = created
)