package com.practice.rickyandmorty.domain.usecase

import com.practice.rickyandmorty.domain.model.Favorite
import com.practice.rickyandmorty.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke(): Flow<List<Favorite>> = repository.getFavorites()
}

class InsertFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(favorite: Favorite) = repository.insert(favorite)
}

class GetIsFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke(id: Int) = repository.isFavorite(id)
}

class DeleteFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}