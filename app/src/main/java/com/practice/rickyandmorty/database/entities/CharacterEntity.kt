package com.practice.rickyandmorty.database.entities

import androidx.room.Entity

@Entity("character", primaryKeys = ["id", "queryKey"])
data class CharacterEntity(
    val id: Int,
    val queryKey: String,
    val name: String? = "Unknown",
    val status: String? = "Unknown",
    val species: String? = "Unknown",
    val type: String? = "Unknown",
    val gender: String? = "Unknown",
    val image: String? = "Unknown",
    val origin: String? = null,
    val location: String? = null,
    val episodes: String? = null,
    val url: String? = null,
    val created: String? = null
)
