package com.practice.rickyandmorty.data.di

import com.practice.rickyandmorty.BuildConfig
import com.practice.rickyandmorty.data.remote.RickAndMortyService
import com.practice.rickyandmorty.data.repository.CharacterRepositoryImpl
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import com.practice.rickyandmorty.domain.usecase.GetCharactersByFilterUseCase
import com.practice.rickyandmorty.domain.usecase.GetCharacterByIdUseCase
import com.practice.rickyandmorty.domain.usecase.GetMultipleCharactersUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RickyAndMortyModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyApi(moshi: Moshi, okHttpClient: OkHttpClient): RickAndMortyService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RickAndMortyService::class.java)

    @Provides
    @Singleton
    fun provideCharacterRepository(api: RickAndMortyService): CharacterRepository =
        CharacterRepositoryImpl(api)

    @Provides
    fun provideGetAllCharactersUseCase(repository: CharacterRepository) =
        GetCharactersByFilterUseCase(repository)

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
