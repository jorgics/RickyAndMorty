package com.practice.rickyandmorty.data.mapper

import com.practice.rickyandmorty.data.remote.model.CharacterDto
import com.practice.rickyandmorty.data.remote.model.InfoDto
import com.practice.rickyandmorty.data.remote.response.RickyAndMortyResponse
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.Info
import com.practice.rickyandmorty.domain.model.RickyAndMorty

fun InfoDto.toDomain(): Info = Info(
    count = count,
    pages = pages,
    next = next,
    prev = prev
)

fun RickyAndMortyResponse<List<CharacterDto>>.toDomain():
        RickyAndMorty<List<Character>> = RickyAndMorty(
    info = info?.toDomain(),
    results = results?.map { it.toDomain() }
)