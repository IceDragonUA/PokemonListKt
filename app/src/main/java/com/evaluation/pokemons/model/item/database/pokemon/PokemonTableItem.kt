package com.evaluation.pokemons.model.item.database.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "pokemons")
data class PokemonTableItem(
    @PrimaryKey
    var index: Int,
    val name: String?,
    val weight: Int?,
    val height: Int?,
    val experience: Int?,
    val front_default: String?,
    val back_default: String?
)