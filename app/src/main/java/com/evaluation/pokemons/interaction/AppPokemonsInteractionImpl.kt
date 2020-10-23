package com.evaluation.pokemons.interaction

import androidx.annotation.MainThread
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.datasource.AppPokemonDataSourceFactory
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.pokemons.repository.AppPokemonsRepository
import com.evaluation.utils.Listing
import com.evaluation.utils.PAGE_OFFSET
import com.evaluation.utils.PAGE_SIZE
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.Executor
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
class AppPokemonsInteractionImpl @Inject constructor(
    private val factory: AppPokemonDataSourceFactory,
    private val config: PagedList.Config,
    private val networkExecutor: Executor,
    private val repository: AppPokemonsRepository
) : AppPokemonsInteraction {

    @MainThread
    override fun pokemonList(language: String, query: String): Listing<BaseItemView> {

        factory.language = language
        factory.query = query

        val liveList =
            LivePagedListBuilder(factory, config)
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
            pagedList = liveList,
            networkState = factory.network
        )
    }

    @MainThread
    override fun langList(): BehaviorProcessor<MutableList<LanguageView>> {
        val processor = BehaviorProcessor.create<MutableList<LanguageView>>()
        repository.languageList(offset = PAGE_OFFSET, limit = PAGE_SIZE).toFlowable().subscribe(processor)
        return processor
    }
}
