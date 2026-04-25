package com.practice.challengebemobile.data.repository

import com.practice.challengebemobile.core.data.network.safeApiCall
import com.practice.challengebemobile.core.data.responses.BaseResponse
import com.practice.challengebemobile.data.mapper.toDomain
import com.practice.challengebemobile.data.remote.RickAndMortyService
import com.practice.challengebemobile.domain.model.Character
import com.practice.challengebemobile.domain.model.CharacterFilter
import com.practice.challengebemobile.domain.model.Gender
import com.practice.challengebemobile.domain.model.Status
import com.practice.challengebemobile.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CharacterRepository {
    override suspend fun getAllCharacters(): BaseResponse<List<Character>> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getAllCharacters().results.let {
                    if (it.isNullOrEmpty()) {
                        emptyList()
                    } else {
                        it.map { characterDto -> characterDto.toDomain() }
                    }
                }
            }
        }
    }

    override suspend fun getCharacterById(id: Int): BaseResponse<Character?> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getCharacterById(id).toDomain()
            }
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): BaseResponse<List<Character>> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getMultipleCharacters(ids.toString()).map { it.toDomain() }
            }
        }
    }

    override suspend fun getCharactersByFilter(characterFilter: CharacterFilter): BaseResponse<List<Character>> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getCharactersByFilter(
                    name = characterFilter.name,
                    status = characterFilter.status?.value ?: Status.Unknown.value,
                    species = characterFilter.species,
                    type = characterFilter.type,
                    gender = characterFilter.gender?.value ?: Gender.Unknown.value
                ).map { it.toDomain() }
            }
        }
    }
}
