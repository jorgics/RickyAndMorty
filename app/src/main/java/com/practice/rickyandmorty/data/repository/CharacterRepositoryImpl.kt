package com.practice.rickyandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.practice.rickyandmorty.core.data.network.safeApiCall
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.data.mapper.toDomain
import com.practice.rickyandmorty.data.remote.RickAndMortyService
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.RickyAndMorty
import com.practice.rickyandmorty.domain.model.toQueryMap
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CharacterRepository {
    override suspend fun getAllCharacters(): BaseResponse<RickyAndMorty<List<Character>>> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getAllCharacters().toDomain()
            }
        }
    }

    override fun getPagedCharacters(filter: CharacterFilter): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(api, filter)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getCharacterById(id: Int): BaseResponse<Character?> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getCharacterById(id).toDomain()
            }
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): BaseResponse<RickyAndMorty<List<Character>>> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getMultipleCharacters(ids.toString()).toDomain()
            }
        }
    }

    override suspend fun getCharactersByFilter(characterFilter: CharacterFilter): BaseResponse<RickyAndMorty<List<Character>>> {
        return withContext(dispatcher) {
            safeApiCall {
                api.getCharactersByFilter(characterFilter.toQueryMap()).toDomain()
            }
        }
    }
}
