package com.practice.rickyandmorty.domain.model

data class Character(
    val id: Int?,
    val name: String? = "Unknown",
    val status: String? = "Unknown",
    val species: String? = "Unknown",
    val type: String? = "Unknown",
    val gender: String? = "Unknown",
    val image: String?,
    val origin: Origin?,
    val location: Location?,
    val episode: List<String?>?,
    val url: String?,
    val created: String?
)
