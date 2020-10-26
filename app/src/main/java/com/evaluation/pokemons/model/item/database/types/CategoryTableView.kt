package com.evaluation.pokemons.model.item.database.types

import androidx.room.Relation

/**
 * @author Vladyslav Havrylenko
 * @since 26.10.2020
 */
data class CategoryTableView(
    var index: Int,
    val name: String?,

    @Relation(parentColumn = "index", entityColumn = "index")
    var pokemons: List<CategoryPokemonTableItem>,
)