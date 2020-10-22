package com.evaluation.pokemons.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evaluation.pokemons.model.item.database.*

@Dao
interface AppPokemonsDatabaseDao {

    @Query("SELECT * FROM pokemons ORDER BY `index` ASC")
    fun pokemonList(): List<PokemonTableView>

    @Query("SELECT * FROM pokemons ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(limit: Int, offset: Int): List<PokemonTableView>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(items: List<PokemonTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatistics(items: List<PokemonStatTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAbilities(items: List<PokemonAbilityTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(items: List<PokemonTypeTableItem>)

    @Query("DELETE FROM pokemons")
    fun deleteList()

    @Query("DELETE FROM statistics")
    fun deleteStatistic()

    @Query("DELETE FROM abilities")
    fun deleteAbilities()

    @Query("DELETE FROM types")
    fun deleteTypes()

}