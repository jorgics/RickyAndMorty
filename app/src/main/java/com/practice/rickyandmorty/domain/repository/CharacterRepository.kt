package com.practice.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.RickyAndMorty
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharacters(): BaseResponse<RickyAndMorty<List<Character>>>
    fun getPagedCharacters(filter: CharacterFilter): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): BaseResponse<Character?>
    suspend fun getMultipleCharacters(ids: List<Int>): BaseResponse<RickyAndMorty<List<Character>>>
    suspend fun getCharactersByFilter(characterFilter: CharacterFilter): BaseResponse<RickyAndMorty<List<Character>>>
}
