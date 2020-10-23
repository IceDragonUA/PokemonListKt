package com.evaluation.pokemons.network

import com.evaluation.pokemons.model.item.rest.language.LanguageList
import com.evaluation.pokemons.model.item.rest.pokemon.PokemonList
import io.reactivex.Single

interface AppPokemonsRestApiDao {

    fun pokemonList(offset: Int, limit: Int): Single<PokemonList>

    fun languageList(offset: Int, limit: Int): Single<LanguageList>

}