package com.practice.rickyandmorty.database.entities

import androidx.room.Entity

@Entity(tableName = "character_remote_keys", primaryKeys = ["characterId", "queryKey"])
data class CharacterRemoteKeysEntity(
    val characterId: Int,
    val queryKey: String,
    val prevKey: Int?,
    val nextKey: Int?
)
