package com.evaluation.pokemons.model.item.database

import androidx.room.Relation

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
data class PokemonTableView(
    var index: Int,
    val name: String?,
    val weight: Int?,
    val height: Int?,
    val experience: Int?,
    val front_default: String?,
    val back_default: String?,

    @Relation(parentColumn = "index", entityColumn = "index")
    var stats: List<PokemonStatTableItem>,

    @Relation(parentColumn = "index", entityColumn = "index")
    var abilities: List<PokemonAbilityTableItem>,

    @Relation(parentColumn = "index", entityColumn = "index")
    var types: List<PokemonTypeTableItem>
)