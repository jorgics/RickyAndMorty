package com.practice.rickyandmorty.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.practice.rickyandmorty.core.extensions.extractPageNumber
import com.practice.rickyandmorty.data.mapper.toEntity
import com.practice.rickyandmorty.data.remote.RickAndMortyService
import com.practice.rickyandmorty.database.AppDatabase
import com.practice.rickyandmorty.database.entities.CharacterEntity
import com.practice.rickyandmorty.database.entities.CharacterRemoteKeysEntity
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.toQueryKey
import com.practice.rickyandmorty.domain.model.toQueryMap
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val apiService: RickAndMortyService,
    private val database: AppDatabase,
    filter: CharacterFilter
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao = database.characterDao()
    private val remoteKeysDao = database.characterRemoteKeysDao()
    private val queryMap = filter.toQueryMap()
    private val queryKey = filter.toQueryKey()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosesToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextKey
                }
            }

            val response = apiService.getCharactersByFilter(page, queryMap)
            val characters = response.results ?: emptyList()

            val prevKey = response.info?.prev.extractPageNumber()
            val nextKey = response.info?.next.extractPageNumber()
            val endOfPaginationReached = nextKey == null

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys(queryKey)
                    characterDao.clearAll(queryKey)
                }

                val entities = characters.map { it.toEntity(queryKey) }
                val keys = entities.map { character ->
                    CharacterRemoteKeysEntity(
                        characterId = character.id,
                        queryKey = queryKey,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                remoteKeysDao.insertAll(keys)
                characterDao.insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let { item ->
                remoteKeysDao.remoteKeysByCharacterId(item.id, item.queryKey)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let { item ->
                remoteKeysDao.remoteKeysByCharacterId(item.id, item.queryKey)
            }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeysEntity? {
        return state.anchorPosition
            ?.let { position -> state.closestItemToPosition(position)?.id }
            ?.let { id -> remoteKeysDao.remoteKeysByCharacterId(id, queryKey) }
    }
}