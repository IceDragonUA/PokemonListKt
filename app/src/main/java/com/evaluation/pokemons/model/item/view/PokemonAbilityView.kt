package com.evaluation.pokemons.model.item.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Parcelize
data class PokemonAbilityView(
    val name: String,
) : Parcelable