package com.practice.rickyandmorty.data.repository

import com.practice.rickyandmorty.data.mapper.toDomain
import com.practice.rickyandmorty.data.mapper.toEntity
import com.practice.rickyandmorty.database.AppDatabase
import com.practice.rickyandmorty.domain.model.Favorite
import com.practice.rickyandmorty.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : FavoriteRepository {

    override fun isFavorite(id: Int): Flow<Boolean> = database.favoritesDao().isFavorite(id)

    override fun getFavorites(): Flow<List<Favorite>> =
        database.favoritesDao().getFavorites()
            .map { favorites ->
                favorites.map { it.toDomain() }
            }

    override suspend fun insert(favorite: Favorite) =
        database.favoritesDao().insert(favorite.toEntity())


    override suspend fun delete(id: Int) =
        database.favoritesDao().delete(id)
}