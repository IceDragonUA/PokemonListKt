package com.evaluation.pokemons.model.item.view.types

import android.os.Parcelable
import com.evaluation.pokemons.model.item.view.pokemon.PokemonView
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 26.10.2020
 */
@Parcelize
data class CategoryView(
    val name: String,
    var pokemons: List<PokemonView>,
) : Parcelable