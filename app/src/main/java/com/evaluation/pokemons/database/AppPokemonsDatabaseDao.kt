package com.evaluation.pokemons.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.*
import com.evaluation.pokemons.model.item.database.types.CategoryPokemonTableItem
import com.evaluation.pokemons.model.item.database.types.CategoryTableItem
import com.evaluation.pokemons.model.item.database.types.CategoryTableView

@Dao
interface AppPokemonsDatabaseDao {

    @Query("SELECT * FROM languages ORDER BY `index` ASC")
    fun languageList(): List<LanguageTableItem>

    @Query("SELECT * FROM pokemons ORDER BY `index` ASC")
    fun pokemonList(): List<PokemonTableView>

    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :filter || '%' ORDER BY `index` ASC ")
    fun pokemonList(filter: String): List<PokemonTableView>

    @Query("SELECT * FROM pokemons ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(limit: Int, offset: Int): List<PokemonTableView>

    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :filter || '%' ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(limit: Int, offset: Int, filter: String): List<PokemonTableView>

    @Query("SELECT * FROM categories ORDER BY `index` ASC")
    fun categoryList(): List<CategoryTableView>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLanguageList(items: List<LanguageTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonList(items: List<PokemonTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatistics(items: List<PokemonStatTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAbilities(items: List<PokemonAbilityTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(items: List<PokemonTypeTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(items: List<CategoryTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryPokemons(items: List<CategoryPokemonTableItem>)

    @Query("DELETE FROM languages")
    fun deleteLanguageList()

    @Query("DELETE FROM pokemons")
    fun deletePokemonList()

    @Query("DELETE FROM statistics")
    fun deleteStatistic()

    @Query("DELETE FROM abilities")
    fun deleteAbilities()

    @Query("DELETE FROM types")
    fun deleteTypes()

    @Query("DELETE FROM categories")
    fun deleteCategories()

    @Query("DELETE FROM category_pokemons")
    fun deleteCategoryPokemons()

}