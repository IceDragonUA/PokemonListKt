package com.evaluation.pokemons.model.item.rest.types

import com.evaluation.pokemons.model.item.rest.language.LanguageName
import com.google.gson.annotations.SerializedName

data class TypeResult(
    @SerializedName("names")
    val names: List<LanguageName>,
    @SerializedName("pokemon")
    val pokemons: List<TypePokemon>,
)