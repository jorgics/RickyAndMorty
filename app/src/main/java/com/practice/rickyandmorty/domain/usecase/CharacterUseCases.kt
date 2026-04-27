package com.practice.rickyandmorty.domain.usecase

import androidx.paging.PagingData
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.RickyAndMorty
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    operator fun invoke(filter: CharacterFilter): Flow<PagingData<Character>> = repository.getPagedCharacters(filter)
}

class GetCharacterByIdUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(id: Int): BaseResponse<Character?> = repository.getCharacterById(id)
}

class GetMultipleCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(ids: List<Int>): BaseResponse<RickyAndMorty<List<Character>>> =
        repository.getMultipleCharacters(ids)
}

class GetCharactersByFilterUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(characterFilter: CharacterFilter): BaseResponse<RickyAndMorty<List<Character>>> =
        repository.getCharactersByFilter(characterFilter)
}
