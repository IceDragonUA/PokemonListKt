package com.evaluation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.model.item.database.PokemonAbilityTableItem
import com.evaluation.pokemons.model.item.database.PokemonStatTableItem
import com.evaluation.pokemons.model.item.database.PokemonTableItem
import com.evaluation.pokemons.model.item.database.PokemonTypeTableItem
import com.evaluation.utils.DATABASE_VERSION

@Database(
    entities = [
        PokemonTableItem::class,
        PokemonStatTableItem::class,
        PokemonAbilityTableItem::class,
        PokemonTypeTableItem::class
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appListDao(): AppPokemonsDatabaseDao

}