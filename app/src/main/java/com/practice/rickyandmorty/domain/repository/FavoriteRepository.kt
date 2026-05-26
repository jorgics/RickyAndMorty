package com.practice.rickyandmorty.domain.repository

import com.practice.rickyandmorty.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun isFavorite(id: Int): Flow<Boolean>
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun insert(favorite: Favorite)
    suspend fun delete(id: Int)
}