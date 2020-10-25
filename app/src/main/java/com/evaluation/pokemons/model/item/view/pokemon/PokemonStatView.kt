package com.evaluation.pokemons.model.item.view.pokemon

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Parcelize
data class PokemonStatView(
    val names: List<LanguageNameView>,
) : Parcelable, PokemonInfo {
    override fun names(): List<LanguageNameView> = names
}