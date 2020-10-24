package com.evaluation.pokemons.interaction

import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.utils.Listing
import io.reactivex.processors.BehaviorProcessor

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
interface AppPokemonsInteraction {

    fun pokemonList(query: String): Listing<BaseItemView>

    fun langList(): BehaviorProcessor<MutableList<LanguageView>>

}