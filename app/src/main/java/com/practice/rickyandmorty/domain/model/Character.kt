package com.practice.rickyandmorty.domain.model

data class Character(
    val id: Int,
    val name: String? = "Unknown",
    val status: String? = "Unknown",
    val species: String? = "Unknown",
    val type: String? = "Unknown",
    val gender: String? = "Unknown",
    val image: String? = "Unknown",
    val origin: String? = null,
    val location: String? = null,
    val episode: List<String?>? = null,
    val url: String? = null,
    val created: String? = null
)
