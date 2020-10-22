package com.evaluation.pokemons.adapter.viewholder

import android.view.View
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.utils.defIfNull
import com.evaluation.utils.initText
import com.evaluation.utils.loadFromUrl
import kotlinx.android.synthetic.main.card_item.view.*


class CardItemHolder(itemView: View, listener: AdapterItemClickListener<CardItemView>?) :
    BaseViewHolder<CardItemView>(itemView, listener) {

    override fun bind(item: CardItemView) {
        itemView.image.loadFromUrl(item.viewItem.front_default)
        itemView.name.initText(item.viewItem.name)
        itemView.abilities.initText(item.viewItem.abilities.joinToString { it.name })
        itemView.setOnClickListener {
            listener?.onClicked(item)
        }
    }

}