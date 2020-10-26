package com.evaluation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonAbilityTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonStatTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTypeTableItem
import com.evaluation.pokemons.model.item.database.types.CategoryPokemonTableItem
import com.evaluation.pokemons.model.item.database.types.CategoryTableItem
import com.evaluation.utils.DATABASE_VERSION

@Database(
    entities = [
        PokemonTableItem::class,
        PokemonStatTableItem::class,
        PokemonAbilityTableItem::class,
        PokemonTypeTableItem::class,

        LanguageTableItem::class,

        CategoryTableItem::class,
        CategoryPokemonTableItem::class
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appListDao(): AppPokemonsDatabaseDao

}