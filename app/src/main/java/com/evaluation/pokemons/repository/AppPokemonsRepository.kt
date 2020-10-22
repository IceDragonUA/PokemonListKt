package com.evaluation.pokemons.repository

import android.content.Context
import com.evaluation.R
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.utils.defIfNull
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 01.05.2020
 */
class AppPokemonsRepository @Inject constructor(
    private val context: Context,
    private val mapper: PokemonMapper,
    private val appRestApiDao: AppPokemonsRestApiDao,
    private val appDatabaseDao: AppPokemonsDatabaseDao
) {

    fun pokemonListInit(
        offset: Int,
        limit: Int,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: (MutableList<BaseItemView>) -> Unit
    ): Disposable {
        return appRestApiDao.pokemonList(offset = offset, limit = limit)
            .doOnSubscribe {
                onPrepared()
            }
            .subscribe(
                { pokemonList ->
                    appDatabaseDao.deleteList()
                    appDatabaseDao.deleteStatistic()
                    appDatabaseDao.deleteAbilities()
                    appDatabaseDao.deleteTypes()

                    val pokemons = pokemonList.pokemons.mapIndexed { rawIndex, pokemon ->
                        val index = offset + rawIndex + 1
                        val tableItem = mapper.toTableItem(pokemon, index)
                        val stats = pokemon.stats.map {
                            mapper.toTableItem(it, index)
                        }
                        val abilities = pokemon.abilities.map {
                            mapper.toTableItem(it, index)
                        }
                        val types = pokemon.types.map {
                            mapper.toTableItem(it, index)
                        }
                        mapper.bufferedEntity(tableItem, stats, abilities, types)
                    }

                    appDatabaseDao.insertList(pokemons.map { it.item })
                    pokemons.forEach {
                        appDatabaseDao.insertStatistics(it.stats)
                        appDatabaseDao.insertAbilities(it.abilities)
                        appDatabaseDao.insertTypes(it.types)
                    }


                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseProgramList = appDatabaseDao.pokemonList()
                    databaseProgramList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.index.defIfNull().toString(),
                                next = pokemonList.next,
                                viewItem = mapper.toViewItem(it)
                            )
                        )
                    }
                    itemList.ifEmpty {
                        itemList.add(
                            NoItemView(
                                title = context.resources.getString(R.string.result).defIfNull()
                            )
                        )
                    }

                    onSuccess(itemList)
                },
                { errorMessage ->
                    Timber.e(errorMessage, "Loading error")

                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseProgramList = appDatabaseDao.pokemonList()
                    databaseProgramList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.index.defIfNull().toString(),
                                next = null,
                                viewItem = mapper.toViewItem(it)
                            )
                        )
                    }
                    itemList.ifEmpty {
                        itemList.add(
                            NoItemView(
                                title = context.resources.getString(R.string.result).defIfNull()
                            )
                        )
                    }

                    onError(itemList)
                }
            )
    }

    fun programListPaged(
        offset: Int,
        limit: Int,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: () -> Unit
    ): Disposable {
        return appRestApiDao.pokemonList(offset = offset, limit = limit)
            .doOnSubscribe {
                onPrepared()
            }
            .subscribe(
                { pokemonList ->
                    val pokemons = pokemonList.pokemons.mapIndexed { rawIndex, pokemon ->
                        val index = offset + rawIndex + 1
                        val tableItem = mapper.toTableItem(pokemon, index)
                        val stats = pokemon.stats.map {
                            mapper.toTableItem(it, index)
                        }
                        val abilities = pokemon.abilities.map {
                            mapper.toTableItem(it, index)
                        }
                        val types = pokemon.types.map {
                            mapper.toTableItem(it, index)
                        }
                        mapper.bufferedEntity(tableItem, stats, abilities, types)
                    }

                    appDatabaseDao.insertList(pokemons.map { it.item })
                    pokemons.forEach {
                        appDatabaseDao.insertStatistics(it.stats)
                        appDatabaseDao.insertAbilities(it.abilities)
                        appDatabaseDao.insertTypes(it.types)
                    }


                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseProgramList = appDatabaseDao.pokemonPagedList(offset, limit)

                    databaseProgramList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.index.defIfNull().toString(),
                                next = pokemonList.next,
                                viewItem = mapper.toViewItem(it)
                            )
                        )
                    }

                    onSuccess(itemList)
                },
                { errorMessage ->
                    Timber.e(errorMessage, "Loading error")
                    onError()
                }
            )
    }
}
