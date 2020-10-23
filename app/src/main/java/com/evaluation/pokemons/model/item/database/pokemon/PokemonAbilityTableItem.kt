package com.evaluation.pokemons.model.item.database.pokemon

import androidx.room.*

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "abilities",
    foreignKeys = [
        ForeignKey(
            entity = PokemonTableItem::class,
            parentColumns = ["index"],
            childColumns = ["index"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonAbilityTableItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var index: Int,
    val name: String,
)