package com.practice.rickyandmorty.data.remote

import com.practice.rickyandmorty.data.remote.model.CharacterDto
import com.practice.rickyandmorty.data.remote.response.RickyAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RickAndMortyService {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDto

    @GET("character")
    suspend fun getCharactersByFilter(
        @QueryMap filters: Map<String, String>
    ): RickyAndMortyResponse<List<CharacterDto>>
}
