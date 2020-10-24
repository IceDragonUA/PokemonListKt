package com.evaluation.pokemons.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.model.item.view.language.LanguageView


/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class PokemonViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction
) : ViewModel() {

    private val queryResult = MutableLiveData<String>()
    private val itemResult = map(queryResult) { interaction.pokemonList(it) }
    val items = switchMap(itemResult) { it.pagedList }
    val networkState = switchMap(itemResult) { it.networkState }

    fun search(query: String) {
        this.queryResult.value = query
    }

}