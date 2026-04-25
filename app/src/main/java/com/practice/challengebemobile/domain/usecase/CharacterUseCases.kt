package com.practice.challengebemobile.domain.usecase

import com.practice.challengebemobile.core.data.responses.BaseResponse
import com.practice.challengebemobile.domain.model.Character
import com.practice.challengebemobile.domain.repository.CharacterRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(): BaseResponse<List<Character>> = repository.getAllCharacters()
}

class GetCharacterByIdUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(id: Int): BaseResponse<Character?> = repository.getCharacterById(id)
}

class GetMultipleCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(ids: List<Int>): BaseResponse<List<Character>> = repository.getMultipleCharacters(ids)
}

class GetCharactersByFilterUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(characterFilter: com.practice.challengebemobile.domain.model.CharacterFilter): BaseResponse<List<Character>> =
        repository.getCharactersByFilter(characterFilter)
}
