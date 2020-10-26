package com.evaluation.pokemons.model.item.rest.types

import com.evaluation.pokemons.model.ResponseResult
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class TypePokemonList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<ResponseResult>,
) {
    private var _types: MutableList<TypeResult>? = null
    val types: MutableList<TypeResult>
        get() {
            if (_types == null) {
                _types = mutableListOf()
            }
            return _types ?: throw AssertionError("Set to null by another thread")
        }
}