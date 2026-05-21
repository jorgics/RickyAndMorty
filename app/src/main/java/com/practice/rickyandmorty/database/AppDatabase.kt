package com.practice.rickyandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practice.rickyandmorty.database.dao.CharacterDao
import com.practice.rickyandmorty.database.dao.CharacterRemoteKeysDao
import com.practice.rickyandmorty.database.entities.CharacterEntity
import com.practice.rickyandmorty.database.entities.CharacterRemoteKeysEntity

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao
    abstract fun characterRemoteKeysDao() : CharacterRemoteKeysDao
}