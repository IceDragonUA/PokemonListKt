package com.evaluation.pokemons.model.item.rest.types

import com.evaluation.pokemons.model.ResponseResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class TypePokemon(
    @SerializedName("pokemon")
    val responseResult: ResponseResult,

    var pokemonResult: Pokemon
)