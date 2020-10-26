package com.evaluation.pokemons.interaction

import androidx.annotation.MainThread
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.datasource.AppCategoryDataSourceFactory
import com.evaluation.pokemons.datasource.AppPokemonDataSourceFactory
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.pokemons.model.item.view.types.CategoryView
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
    private val pokemonFactory: AppPokemonDataSourceFactory,
    private val categoryFactory: AppCategoryDataSourceFactory,
    private val config: PagedList.Config,
    private val networkExecutor: Executor,
    private val repository: AppPokemonsRepository
) : AppPokemonsInteraction {

    @MainThread
    override fun pokemonList(query: String): Listing<BaseItemView> {

        pokemonFactory.query = query

        val liveList =
            LivePagedListBuilder(pokemonFactory, config)
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
            pagedList = liveList,
            networkState = pokemonFactory.network
        )
    }

    @MainThread
    override fun categoryList(query: String, category: CategoryView): Listing<BaseItemView> {

        categoryFactory.query = query
        categoryFactory.category = category

        val liveList =
            LivePagedListBuilder(categoryFactory, config)
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
            pagedList = liveList,
            networkState = categoryFactory.network
        )
    }

    @MainThread
    override fun langList(): BehaviorProcessor<MutableList<LanguageView>> {
        val processor = BehaviorProcessor.create<MutableList<LanguageView>>()
        repository.languageList(offset = PAGE_OFFSET, limit = PAGE_SIZE).toFlowable().subscribe(processor)
        return processor
    }

    @MainThread
    override fun categoryList(): BehaviorProcessor<MutableList<CategoryView>> {
        val processor = BehaviorProcessor.create<MutableList<CategoryView>>()
        repository.typeList(offset = PAGE_OFFSET, limit = PAGE_SIZE).toFlowable().subscribe(processor)
        return processor
    }
}
