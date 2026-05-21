package com.practice.rickyandmorty.database.di

import android.content.Context
import androidx.room.Room
import com.practice.rickyandmorty.database.AppDatabase
import com.practice.rickyandmorty.database.dao.CharacterDao
import com.practice.rickyandmorty.database.dao.CharacterRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):
            AppDatabase = Room.databaseBuilder(
        context = context,
        AppDatabase::class.java,
        "database"
    ).build()

    @Provides
    @Singleton
    fun provideCharacterDao(
        database: AppDatabase
    ) : CharacterDao = database.characterDao()

    @Provides
    @Singleton
    fun provideCharacterRemoteKeysDao(
        database: AppDatabase
    ) : CharacterRemoteKeysDao = database.characterRemoteKeysDao()
}