package com.eslimaf.coroutines.data.marvel

import com.eslimaf.coroutines.data.marvel.api.model.ApiResponse
import com.eslimaf.coroutines.data.marvel.api.model.entity.Character
import com.eslimaf.coroutines.data.marvel.api.model.entity.Comic
import com.eslimaf.coroutines.data.marvel.api.model.entity.Series
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MarvelApi {
    @GET("characters")
    fun getCharacters(@Query("offset") offset: Int? = 0): Call<ApiResponse<Character>>

    @GET("characters")
    fun getCharactersEx(@Query("offset") offset: Int? = 0): Deferred<ApiResponse<Character>>

    @GET("characters/{id}")
    fun getCharacterDetails(@Path("id") id: Int? = 0): Deferred<ApiResponse<Character>>

    @GET("characters/{id}/comics")
    fun getCharacterComics(@Path("id") id: Int? = 0): Deferred<ApiResponse<Comic>>

    @GET("characters/{id}/series")
    fun getCharacterSeries(@Path("id") id: Int? = 0): Deferred<ApiResponse<Series>>
}