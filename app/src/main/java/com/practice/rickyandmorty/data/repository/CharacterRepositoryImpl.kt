package com.practice.rickyandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.practice.rickyandmorty.core.data.network.safeApiCall
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.data.mapper.toDomain
import com.practice.rickyandmorty.data.mediator.CharacterRemoteMediator
import com.practice.rickyandmorty.data.remote.RickAndMortyService
import com.practice.rickyandmorty.database.AppDatabase
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.toQueryKey
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyService,
    private val database: AppDatabase
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedCharacters(filter: CharacterFilter): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                apiService = api,
                database = database,
                filter = filter
            ),
            pagingSourceFactory = {
                database.characterDao().pagingSource(filter.toQueryKey())
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getCharacterById(id: Int): BaseResponse<Character?> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                database.characterDao().getCharacterById(id).toDomain()
            }
        }
    }
}
