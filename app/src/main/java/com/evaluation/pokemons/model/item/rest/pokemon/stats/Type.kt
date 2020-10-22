package com.evaluation.pokemons.model.item.rest.pokemon.stats

import com.evaluation.pokemons.model.ResponseResult
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class Type(
    @SerializedName("type")
    val type: ResponseResult,

    var langType: LanguageResult
)