package com.evaluation.pokemons.interaction

import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.Listing

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
interface AppPokemonsInteraction {

    fun pokemonList(): Listing<BaseItemView>

}