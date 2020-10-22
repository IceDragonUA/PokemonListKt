package com.evaluation.pokemons.mapper

import com.evaluation.pokemons.model.item.database.*
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Type
import com.evaluation.pokemons.model.item.view.PokemonAbilityView
import com.evaluation.pokemons.model.item.view.PokemonStatView
import com.evaluation.pokemons.model.item.view.PokemonTypeView
import com.evaluation.pokemons.model.item.view.PokemonView
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

    fun toTableItem(item: Stat, index: Int): PokemonStatTableItem {
        return PokemonStatTableItem(
            index = index,
            name = item.langStat?.names?.get(0)?.name.defIfNull()
        )
    }

    fun toTableItem(item: Ability, index: Int): PokemonAbilityTableItem {
        return PokemonAbilityTableItem(
            index = index,
            name = item.langAbility?.names?.get(0)?.name.defIfNull()
        )
    }

    fun toTableItem(item: Type, index: Int): PokemonTypeTableItem {
        return PokemonTypeTableItem(
            index = index,
            name = item.langType?.names?.get(0)?.name.defIfNull()
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