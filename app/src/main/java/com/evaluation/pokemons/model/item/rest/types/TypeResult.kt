package com.evaluation.pokemons.model.item.rest.types

import com.google.gson.annotations.SerializedName

data class TypeResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("pokemon")
    val pokemons: List<TypePokemon>,
)