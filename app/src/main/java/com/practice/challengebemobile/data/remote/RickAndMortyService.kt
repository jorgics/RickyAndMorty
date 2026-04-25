package com.practice.challengebemobile.data.remote

import com.practice.challengebemobile.core.data.responses.BaseResponse
import com.practice.challengebemobile.data.remote.model.CharacterDto
import com.practice.challengebemobile.data.remote.response.RickyAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character")
    suspend fun getAllCharacters(): RickyAndMortyResponse<List<CharacterDto>>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDto

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") ids: String): List<CharacterDto>

    @GET("character")
    suspend fun getCharactersByFilter(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): List<CharacterDto>
}
