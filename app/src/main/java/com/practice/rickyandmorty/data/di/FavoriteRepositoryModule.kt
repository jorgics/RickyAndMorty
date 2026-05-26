package com.practice.rickyandmorty.data.di

import com.practice.rickyandmorty.data.repository.FavoriteRepositoryImpl
import com.practice.rickyandmorty.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository
}