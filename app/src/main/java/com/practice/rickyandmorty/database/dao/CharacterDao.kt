package com.practice.rickyandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.rickyandmorty.database.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character WHERE queryKey = :queryKey ORDER BY id ASC")
    fun pagingSource(queryKey: String): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("DELETE FROM character WHERE queryKey = :queryKey")
    suspend fun clearAll(queryKey: String)

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity
}