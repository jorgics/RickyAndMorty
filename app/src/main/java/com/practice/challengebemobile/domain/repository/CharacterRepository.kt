package com.practice.challengebemobile.domain.repository

import com.practice.challengebemobile.core.data.responses.BaseResponse
import com.practice.challengebemobile.domain.model.Character
import com.practice.challengebemobile.domain.model.CharacterFilter

interface CharacterRepository {
    suspend fun getAllCharacters(): BaseResponse<List<Character>>
    suspend fun getCharacterById(id: Int): BaseResponse<Character?>
    suspend fun getMultipleCharacters(ids: List<Int>): BaseResponse<List<Character>>
    suspend fun getCharactersByFilter(characterFilter: CharacterFilter): BaseResponse<List<Character>>
}
