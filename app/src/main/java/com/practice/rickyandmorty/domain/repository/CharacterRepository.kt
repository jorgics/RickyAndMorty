package com.practice.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.CharacterFilter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getPagedCharacters(filter: CharacterFilter): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): BaseResponse<Character?>
}
