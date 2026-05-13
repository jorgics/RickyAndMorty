package com.practice.rickyandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterFilter(
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null
)

fun CharacterFilter.toQueryMap(): Map<String, String> {
    return buildMap {
        name?.let { put("name", it) }
        status?.let { put("status", it) }
        species?.let { put("species", it) }
        type?.let { put("type", it) }
        gender?.let { put("gender", it) }
    }
}

sealed class Status(val value: String) {
    data object All : Status("all")
    data object Alive : Status("alive")
    data object Dead : Status("dead")
    data object Unknown : Status("unknown")
}

sealed class Gender(val value: String) {
    data object All : Gender("all")
    data object Female : Gender("female")
    data object Male : Gender("male")
    data object Genderless : Gender("genderless")
    data object Unknown : Gender("unknown")
}