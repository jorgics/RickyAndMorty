package com.practice.rickyandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practice.rickyandmorty.database.dao.CharacterDao
import com.practice.rickyandmorty.database.dao.CharacterRemoteKeysDao
import com.practice.rickyandmorty.database.dao.FavoriteCharacterDao
import com.practice.rickyandmorty.database.entities.CharacterEntity
import com.practice.rickyandmorty.database.entities.CharacterRemoteKeysEntity
import com.practice.rickyandmorty.database.entities.FavoriteEntity

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeysEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
    abstract fun favoritesDao(): FavoriteCharacterDao
}