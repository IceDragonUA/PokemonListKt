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
) {
    private var _pokemons: MutableList<Pokemon>? = null
    val pokemons: MutableList<Pokemon>
        get() {
            if (_pokemons == null) {
                _pokemons = mutableListOf()
            }
            return _pokemons ?: throw AssertionError("Set to null by another thread")
        }
}