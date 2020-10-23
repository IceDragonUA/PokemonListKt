package com.evaluation.pokemons.model.item.database.language

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "languages")
data class LanguageTableItem(
    @PrimaryKey(autoGenerate = true)
    var index: Int? = null,
    val name: String
)