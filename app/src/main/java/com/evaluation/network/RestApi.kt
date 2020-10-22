package com.evaluation.network

import com.evaluation.pokemons.model.item.rest.language.LanguageList
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.rest.pokemon.PokemonList
import com.evaluation.pokemons.model.item.rest.types.TypePokemonList
import com.evaluation.pokemons.model.item.rest.types.TypeResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RestApi {

    @GET("pokemon")
    fun pokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<PokemonList>

    @GET("type")
    fun typeList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<TypePokemonList>

    @GET("language")
    fun languageList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<LanguageList>

    @GET
    fun pokemon(@Url url: String?): Single<Pokemon>

    @GET
    fun paramList(@Url url: String?): Single<LanguageResult>

    @GET
    fun typeList(@Url url: String?): Single<TypeResult>

}