package com.practice.challengebemobile.data.di

import com.practice.challengebemobile.BuildConfig
import com.practice.challengebemobile.data.remote.RickAndMortyService
import com.practice.challengebemobile.data.repository.CharacterRepositoryImpl
import com.practice.challengebemobile.domain.repository.CharacterRepository
import com.practice.challengebemobile.domain.usecase.GetAllCharactersUseCase
import com.practice.challengebemobile.domain.usecase.GetCharacterByIdUseCase
import com.practice.challengebemobile.domain.usecase.GetCharactersByFilterUseCase
import com.practice.challengebemobile.domain.usecase.GetMultipleCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RickyAndMortyModule {
    @Provides
    @Singleton
    fun provideRickAndMortyApi(): RickAndMortyService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RickAndMortyService::class.java)

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickAndMortyService): CharacterRepository =
        CharacterRepositoryImpl(api)

    @Provides
    fun provideGetAllCharactersUseCase(repository: CharacterRepository) =
        GetAllCharactersUseCase(repository)

    @Provides
    fun provideGetCharacterByIdUseCase(repository: CharacterRepository) =
        GetCharacterByIdUseCase(repository)

    @Provides
    fun provideGetMultipleCharactersUseCase(repository: CharacterRepository) =
        GetMultipleCharactersUseCase(repository)

    @Provides
    fun provideGetCharactersByFilterUseCase(repository: CharacterRepository) =
        GetCharactersByFilterUseCase(repository)
}
