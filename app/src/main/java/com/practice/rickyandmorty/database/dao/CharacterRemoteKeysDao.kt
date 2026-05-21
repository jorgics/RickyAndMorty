package com.practice.rickyandmorty.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.rickyandmorty.database.entities.CharacterRemoteKeysEntity

@Dao
interface CharacterRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<CharacterRemoteKeysEntity>)

    @Query("SELECT * FROM character_remote_keys WHERE characterId = :characterId AND queryKey = :queryKey")
    suspend fun remoteKeysByCharacterId(characterId: Int, queryKey: String): CharacterRemoteKeysEntity?

    @Query("DELETE FROM character_remote_keys WHERE queryKey = :queryKey")
    suspend fun clearRemoteKeys(queryKey: String)
}