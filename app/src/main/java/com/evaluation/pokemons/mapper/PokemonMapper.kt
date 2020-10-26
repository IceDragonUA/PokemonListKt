package com.evaluation.pokemons.mapper

import com.evaluation.pokemons.model.ResponseResult
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.*
import com.evaluation.pokemons.model.item.database.types.CategoryPokemonTableItem
import com.evaluation.pokemons.model.item.database.types.CategoryTableItem
import com.evaluation.pokemons.model.item.database.types.CategoryTableView
import com.evaluation.pokemons.model.item.rest.language.Language
import com.evaluation.pokemons.model.item.rest.language.LanguageName
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.stats.Type
import com.evaluation.pokemons.model.item.rest.types.TypePokemon
import com.evaluation.pokemons.model.item.rest.types.TypeResult
import com.evaluation.pokemons.model.item.view.language.LanguageNameView
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.pokemons.model.item.view.pokemon.*
import com.evaluation.pokemons.model.item.view.types.CategoryView
import com.evaluation.utils.defIfNull
import com.evaluation.utils.fromJson
import com.evaluation.utils.toTypedJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
class PokemonMapper @Inject constructor(private val gson: Gson) {

    data class PokemonConverter(
        val item: PokemonTableItem,
        val stats: List<PokemonStatTableItem>,
        val abilities: List<PokemonAbilityTableItem>,
        val types: List<PokemonTypeTableItem>
    )

    data class CategoriesConverter(
        val item: CategoryTableItem,
        val pokemons: List<CategoryPokemonTableItem>,
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
            name = gson.toTypedJson(item.langStat, object : TypeToken<LanguageResult>() {}.type)
        )
    }

    fun toTableItem(item: Ability, index: Int): PokemonAbilityTableItem {
        return PokemonAbilityTableItem(
            index = index,
            name = gson.toTypedJson(item.langAbility, object : TypeToken<LanguageResult>() {}.type)
        )
    }

    fun toTableItem(item: Type, index: Int): PokemonTypeTableItem {
        return PokemonTypeTableItem(
            index = index,
            name = gson.toTypedJson(item.langType, object : TypeToken<LanguageResult>() {}.type)
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
            names = (gson.fromJson(item.name) as LanguageResult).names.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: PokemonAbilityTableItem): PokemonAbilityView {
        return PokemonAbilityView(
            names = (gson.fromJson(item.name) as LanguageResult).names.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: PokemonTypeTableItem): PokemonTypeView {
        return PokemonTypeView(
            names = (gson.fromJson(item.name) as LanguageResult).names.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: LanguageName): LanguageNameView {
        return LanguageNameView(
            name = item.name.defIfNull(),
            language = this.toViewItem(item.language)
        )
    }

    private fun toViewItem(item: Language): LanguageView {
        return LanguageView(
            name = item.name.defIfNull()
        )
    }

    fun toTableItem(item: TypeResult, index: Int): CategoryTableItem {
        return CategoryTableItem(
            index = index,
            name = item.name.defIfNull(),
        )
    }

    fun toTableItem(item: TypePokemon, index: Int): CategoryPokemonTableItem {
        return CategoryPokemonTableItem(
            index = index,
            pokemon = gson.toTypedJson(item.pokemonResult, object : TypeToken<Pokemon>() {}.type)
        )
    }

    fun toViewItem(item: CategoryTableView): CategoryView {
        return CategoryView(
            name = item.name.defIfNull(),
            pokemons = item.pokemons.map { this.toViewItem(gson.fromJson(it.pokemon) as Pokemon) }
        )
    }

    private fun toViewItem(item: Pokemon): PokemonView {
        return PokemonView(
            name = item.name.defIfNull(),
            weight = item.weight.defIfNull(),
            height = item.height.defIfNull(),
            experience = item.experience.defIfNull(),
            front_default = item.sprites.front_default.defIfNull(),
            back_default = item.sprites.back_default.defIfNull(),
            stats = item.stats.map { this.toViewItem(it) },
            abilities = item.abilities.map { this.toViewItem(it) },
            types = item.types.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: Stat): PokemonStatView {
        return PokemonStatView(
            names = item.langStat.names.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: Ability): PokemonAbilityView {
        return PokemonAbilityView(
            names = item.langAbility.names.map { this.toViewItem(it) }
        )
    }

    private fun toViewItem(item: Type): PokemonTypeView {
        return PokemonTypeView(
            names = item.langType.names.map { this.toViewItem(it) }
        )
    }

    fun bufferedEntity(
        item: PokemonTableItem,
        stats: List<PokemonStatTableItem>,
        abilities: List<PokemonAbilityTableItem>,
        types: List<PokemonTypeTableItem>
    ): PokemonConverter {
        return PokemonConverter(
            item = item,
            stats = stats,
            abilities = abilities,
            types = types
        )
    }

    fun bufferedEntity(
        item: CategoryTableItem,
        pokemons: List<CategoryPokemonTableItem>
    ): CategoriesConverter {
        return CategoriesConverter(
            item = item,
            pokemons = pokemons
        )
    }
}