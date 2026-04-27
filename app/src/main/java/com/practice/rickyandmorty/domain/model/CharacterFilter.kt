package com.practice.rickyandmorty.domain.model

data class CharacterFilter(
    val name: String? = null,
    val status: Status? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: Gender? = null
)

fun CharacterFilter.toQueryMap(): Map<String, String> {
    return buildMap {
        name?.let { put("name", it) }
        status?.let { put("status", it.value) }
        species?.let { put("species", it) }
        type?.let { put("type", it) }
        gender?.let { put("gender", it.value) }
    }
}

sealed class Status(val value: String) {
    data object Alive : Status("alive")
    data object Dead : Status("dead")
    data object Unknown : Status("unknown")
}

sealed class Gender(val value: String) {
    data object Female : Gender("female")
    data object Male : Gender("male")
    data object Genderless : Gender("genderless")
    data object Unknown : Gender("unknown")
}