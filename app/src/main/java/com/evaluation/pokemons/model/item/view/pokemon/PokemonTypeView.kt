package com.evaluation.pokemons.model.item.view.pokemon

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
data class PokemonTypeView(
    val names: List<LanguageNameView>,
) : Parcelable, PokemonInfo{
    override fun names(): List<LanguageNameView> = names
}