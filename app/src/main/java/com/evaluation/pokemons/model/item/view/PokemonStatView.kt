package com.evaluation.pokemons.model.item.view

import android.os.Parcelable
import androidx.room.*
import com.evaluation.pokemons.model.item.rest.pokemon.Sprite
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Type
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Parcelize
data class PokemonStatView(
    val name: String,
) : Parcelable