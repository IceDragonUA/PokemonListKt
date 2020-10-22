package com.evaluation.pokemons.model.item.database

import androidx.room.*
import com.evaluation.pokemons.model.item.rest.pokemon.Sprite
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Type
import com.google.gson.annotations.SerializedName

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