package com.evaluation.pokemons.repository

import android.content.Context
import com.evaluation.R
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.adapter.viewholder.item.EmptyItemView
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.utils.NO_ITEM
import com.evaluation.utils.defIfNull
import io.reactivex.Single
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
        query: String,
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
                    appDatabaseDao.deletePokemonList()
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

                    appDatabaseDao.insertPokemonList(pokemons.map { it.item })
                    pokemons.forEach {
                        appDatabaseDao.insertStatistics(it.stats)
                        appDatabaseDao.insertAbilities(it.abilities)
                        appDatabaseDao.insertTypes(it.types)
                    }


                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseList = if (query.isEmpty())
                        appDatabaseDao.pokemonList() else
                        appDatabaseDao.pokemonList(query)
                    databaseList.forEach {
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
                    val databaseList = if (query.isEmpty())
                        appDatabaseDao.pokemonList() else
                        appDatabaseDao.pokemonList(query)
                    databaseList.forEach {
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

    fun pokemonListPaged(
        offset: Int,
        limit: Int,
        query: String,
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

                    appDatabaseDao.insertPokemonList(pokemons.map { it.item })
                    pokemons.forEach {
                        appDatabaseDao.insertStatistics(it.stats)
                        appDatabaseDao.insertAbilities(it.abilities)
                        appDatabaseDao.insertTypes(it.types)
                    }


                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val databaseList = if (query.isEmpty())
                        appDatabaseDao.pokemonPagedList(offset, limit) else
                        appDatabaseDao.pokemonPagedList(offset, limit, query)
                    databaseList.forEach {
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
                            EmptyItemView(
                                index = NO_ITEM + pokemonList.next,
                                next = pokemonList.next
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

    fun languageList(
        offset: Int,
        limit: Int
    ): Single<MutableList<LanguageView>> {
        return appRestApiDao.languageList(offset = offset, limit = limit)
            .map { languageList ->
                appDatabaseDao.deleteLanguageList()
                appDatabaseDao.insertLanguageList(languageList.results.map { mapper.toTableItem(it) })

                val itemList: MutableList<LanguageView> = mutableListOf()
                val databaseList = appDatabaseDao.languageList()

                databaseList.forEach {
                    itemList.add(LanguageView(name = it.name))
                }

                itemList
            }
    }
}
