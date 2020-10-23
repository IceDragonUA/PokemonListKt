package com.evaluation.pokemons.model

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
data class ResponseResult(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)