package com.evaluation.pokemons.network

import com.evaluation.executor.ThreadExecutor
import com.evaluation.network.RestApi
import com.evaluation.pokemons.model.item.rest.language.LanguageList
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.rest.pokemon.PokemonList
import com.evaluation.pokemons.model.item.rest.types.TypePokemonList
import com.evaluation.pokemons.model.item.rest.types.TypeResult
import io.reactivex.Single
import javax.inject.Inject


class AppPokemonsRestApiDaoImpl @Inject constructor(
    private val appRest: RestApi,
    private val executor: ThreadExecutor
) : AppPokemonsRestApiDao {

    override fun pokemonList(offset: Int, limit: Int): Single<PokemonList> {
        return appRest.pokemonList(offset, limit)
            .flatMap {
                val list = mutableListOf<Single<Pokemon>>()
                it.results.forEach { result ->
                    list.add(appRest.pokemon(result.url))
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.pokemons.add(arg as Pokemon)
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<LanguageResult>>()
                it.pokemons.forEach { pokemon ->
                    pokemon.stats.forEach { stat ->
                        list.add(appRest.paramList(stat.stat.url))
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.pokemons.forEach { pokemon ->
                            pokemon.stats.forEach { stat ->
                                if (stat.stat.name == (arg as LanguageResult).name) stat.langStat = arg
                            }
                        }
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<LanguageResult>>()
                it.pokemons.forEach { pokemon ->
                    pokemon.abilities.forEach { ability ->
                        list.add(appRest.paramList(ability.ability.url))
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.pokemons.forEach { pokemon ->
                            pokemon.abilities.forEach { ability ->
                                if (ability.ability.name == (arg as LanguageResult).name) ability.langAbility = arg
                            }
                        }
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<LanguageResult>>()
                it.pokemons.forEach { pokemon ->
                    pokemon.types.forEach { type ->
                        list.add(appRest.paramList(type.type.url))
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.pokemons.forEach { pokemon ->
                            pokemon.types.forEach { type ->
                                if (type.type.name == (arg as LanguageResult).name) type.langType = arg
                            }
                        }
                    }
                    it
                }
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun languageList(offset: Int, limit: Int): Single<LanguageList> {
        return appRest.languageList(offset, limit)
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

    override fun typeList(offset: Int, limit: Int): Single<TypePokemonList> {
        return appRest.typeList(offset, limit)
            .flatMap {
                val list = mutableListOf<Single<TypeResult>>()
                it.results.forEach { result ->
                    list.add(appRest.typeList(result.url))
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.types.add(arg as TypeResult)
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<Pokemon>>()
                it.types.forEach { type ->
                    type.pokemons.forEach { pokemon ->
                        list.add(appRest.pokemon(pokemon.responseResult.url))
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.types.forEach { type ->
                            type.pokemons.forEach { pokemon ->
                                if (pokemon.responseResult.name == (arg as Pokemon).name) pokemon.pokemonResult = arg
                            }
                        }
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<LanguageResult>>()
                it.types.forEach { type ->
                    type.pokemons.forEach { pokemon ->
                        pokemon.pokemonResult.stats.forEach { stat ->
                            list.add(appRest.paramList(stat.stat.url))
                        }
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.types.forEach { type ->
                            type.pokemons.forEach { pokemon ->
                                pokemon.pokemonResult.stats.forEach { stat ->
                                    if (stat.stat.name == (arg as LanguageResult).name) stat.langStat = arg
                                }
                            }
                        }
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<LanguageResult>>()
                it.types.forEach { type ->
                    type.pokemons.forEach { pokemon ->
                        pokemon.pokemonResult.abilities.forEach { stat ->
                            list.add(appRest.paramList(stat.ability.url))
                        }
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.types.forEach { type ->
                            type.pokemons.forEach { pokemon ->
                                pokemon.pokemonResult.abilities.forEach { ability ->
                                    if (ability.ability.name == (arg as LanguageResult).name) ability.langAbility = arg
                                }
                            }
                        }
                    }
                    it
                }
            }
            .flatMap {
                val list = mutableListOf<Single<LanguageResult>>()
                it.types.forEach { type ->
                    type.pokemons.forEach { pokemon ->
                        pokemon.pokemonResult.types.forEach { _type ->
                            list.add(appRest.paramList(_type.type.url))
                        }
                    }
                }
                Single.zip(list) { args ->
                    args.forEach { arg ->
                        it.types.forEach { type ->
                            type.pokemons.forEach { pokemon ->
                                pokemon.pokemonResult.types.forEach { _type ->
                                    if (_type.type.name == (arg as LanguageResult).name) _type.langType = arg
                                }
                            }
                        }
                    }
                    it
                }
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
    }

}