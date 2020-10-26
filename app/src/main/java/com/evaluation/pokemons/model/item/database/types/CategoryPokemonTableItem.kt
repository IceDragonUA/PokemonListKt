package com.evaluation.pokemons.model.item.database.types

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @author Vladyslav Havrylenko
 * @since 26.10.2020
 */
@Entity(tableName = "category_pokemons",
    foreignKeys = [
        ForeignKey(
            entity = CategoryTableItem::class,
            parentColumns = ["index"],
            childColumns = ["index"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CategoryPokemonTableItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var index: Int,
    val pokemon: String
)