package com.evaluation.pokemons.model.item.database.types

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Vladyslav Havrylenko
 * @since 26.10.2020
 */
@Entity(tableName = "categories")
data class CategoryTableItem(
    @PrimaryKey
    var index: Int,
    val name: String,
)