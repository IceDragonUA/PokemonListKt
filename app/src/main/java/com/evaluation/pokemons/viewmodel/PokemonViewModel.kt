package com.evaluation.pokemons.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.model.item.view.types.CategoryView


/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class PokemonViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction
) : ViewModel() {

    private var category: CategoryView? = null
    private val queryResult = MutableLiveData<String>()
    private var itemResult = map(queryResult) { query ->
        category?.let { interaction.categoryList(query, it) } ?: interaction.pokemonList(query)
    }
    val items = switchMap(itemResult) { it.pagedList }
    val networkState = switchMap(itemResult) { it.networkState }

    fun search(query: String, category: CategoryView?) {
        this.category = category
        this.queryResult.value = query
    }

}