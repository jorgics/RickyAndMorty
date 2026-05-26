package com.practice.rickyandmorty.data.di

import com.practice.rickyandmorty.data.repository.CharacterRepositoryImpl
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl
    ): CharacterRepository
}