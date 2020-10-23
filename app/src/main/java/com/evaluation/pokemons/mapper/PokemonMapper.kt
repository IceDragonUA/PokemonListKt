package com.evaluation.pokemons.mapper

import com.evaluation.pokemons.model.ResponseResult
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.*
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Type
import com.evaluation.pokemons.model.item.view.pokemon.PokemonAbilityView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonStatView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonTypeView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonView
import com.evaluation.utils.defIfNull
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
class PokemonMapper @Inject constructor() {

    data class Converter(
        val item: PokemonTableItem,
        val stats: List<PokemonStatTableItem>,
        val abilities: List<PokemonAbilityTableItem>,
        val types: List<PokemonTypeTableItem>
    )

    fun toTableItem(item: Pokemon, index: Int): PokemonTableItem {
        return item.let {
            PokemonTableItem(
                index = index,
                name = it.name.defIfNull(),
                weight = it.weight.defIfNull(),
                height = it.height.defIfNull(),
                experience = it.experience.defIfNull(),
                front_default = it.sprites.front_default.defIfNull(),
                back_default = it.sprites.back_default.defIfNull()
            )
        }
    }

    fun toTableItem(item: Stat, index: Int, language: String): PokemonStatTableItem {
        return PokemonStatTableItem(
            index = index,
            name = item.langStat.names.find { it.language.name == language }?.name.defIfNull()
        )
    }

    fun toTableItem(item: Ability, index: Int, language: String): PokemonAbilityTableItem {
        return PokemonAbilityTableItem(
            index = index,
            name = item.langAbility.names.find { it.language.name == language }?.name.defIfNull()
        )
    }

    fun toTableItem(item: Type, index: Int, language: String): PokemonTypeTableItem {
        return PokemonTypeTableItem(
            index = index,
            name = item.langType.names.find { it.language.name == language }?.name.defIfNull()
        )
    }

    fun toTableItem(item: ResponseResult): LanguageTableItem {
        return LanguageTableItem(
            name = item.name.defIfNull()
        )
    }

    fun toViewItem(item: PokemonTableView): PokemonView {
        return PokemonView(
            name = item.name.defIfNull(),
            weight = item.weight.defIfNull(),
            height = item.height.defIfNull(),
            experience = item.experience.defIfNull(),
            front_default = item.front_default.defIfNull(),
            back_default = item.back_default.defIfNull(),
            stats = item.stats.map { this.toViewItem(it) },
            abilities = item.abilities.map { this.toViewItem(it) },
            types = item.types.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: PokemonStatTableItem): PokemonStatView {
        return PokemonStatView(
            name = item.name.defIfNull()
        )
    }

    private fun toViewItem(item: PokemonAbilityTableItem): PokemonAbilityView {
        return PokemonAbilityView(
            name = item.name.defIfNull()
        )
    }

    private fun toViewItem(item: PokemonTypeTableItem): PokemonTypeView {
        return PokemonTypeView(
            name = item.name.defIfNull()
        )
    }

    fun bufferedEntity(
        item: PokemonTableItem,
        stats: List<PokemonStatTableItem>,
        abilities: List<PokemonAbilityTableItem>,
        types: List<PokemonTypeTableItem>
    ): Converter {
        return Converter(
            item = item,
            stats = stats,
            abilities = abilities,
            types = types
        )
    }
}