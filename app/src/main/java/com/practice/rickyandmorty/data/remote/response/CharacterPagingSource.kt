package com.practice.rickyandmorty.data.remote.response

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.practice.rickyandmorty.data.remote.RickAndMortyService
import com.practice.rickyandmorty.data.remote.model.CharacterDto
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.toQueryMap
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val api: RickAndMortyService,
    private val filter: CharacterFilter
) : PagingSource<Int, CharacterDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        return try {
            val page = params.key ?: 1

            val query = filter.toQueryMap().toMutableMap().apply {
                put("page", page.toString())
            }

            val response = api.getCharactersByFilter(query)
            val data = response.results ?: emptyList()
            val nextPage = if (response.info?.next != null) page + 1 else null

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int? {
        return state.anchorPosition
    }
}