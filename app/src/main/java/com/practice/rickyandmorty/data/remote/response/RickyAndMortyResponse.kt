package com.practice.rickyandmorty.data.remote.response

import com.practice.rickyandmorty.data.remote.model.InfoDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RickyAndMortyResponse<T>(
    @field:Json(name = "info")
    val info: InfoDto?,
    @field:Json(name = "results")
    val results: T?,
    @field:Json(name = "error")
    val errors: String?
)
