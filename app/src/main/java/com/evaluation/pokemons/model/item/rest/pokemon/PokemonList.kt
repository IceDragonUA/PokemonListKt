package com.evaluation.pokemons.model.item.rest.pokemon

import com.evaluation.pokemons.model.ResponseResult
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
data class PokemonList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<ResponseResult>,

    val pokemons: MutableList<Pokemon> = mutableListOf()
)