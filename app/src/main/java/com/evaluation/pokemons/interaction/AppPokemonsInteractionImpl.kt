package com.evaluation.pokemons.interaction

import androidx.annotation.MainThread
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.datasource.AppPokemonDataSourceFactory
import com.evaluation.utils.Listing
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
class AppPokemonsInteractionImpl @Inject constructor(
    private val factory: AppPokemonDataSourceFactory,
    private val config: PagedList.Config,
    private val networkExecutor: Executor
) : AppPokemonsInteraction {

    @MainThread
    override fun pokemonList(): Listing<BaseItemView> {

        val liveList =
            LivePagedListBuilder(factory, config)
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
            pagedList = liveList,
            networkState = factory.network
        )
    }
}
