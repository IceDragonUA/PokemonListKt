package com.evaluation.pokemons.adapter.viewholder.item

import com.evaluation.adapter.factory.TypesFactory
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.model.item.database.PokemonTableItem
import com.evaluation.pokemons.model.item.database.PokemonTableView
import com.evaluation.pokemons.model.item.view.PokemonView

/**
 * @author Vladyslav Havrylenko
 * @since 03.10.2020
 */
data class CardItemView(
    override var index: String,
    override var next: String? = null,
    var viewItem: PokemonView
) : BaseItemView {

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}