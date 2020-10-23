package com.evaluation.adapter.viewholder

import android.view.View
import com.evaluation.adapter.viewholder.item.EmptyItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import kotlinx.android.synthetic.main.no_item.view.*

class EmptyItemHolder(itemView: View) : BaseViewHolder<EmptyItemView>(itemView, null) {

    override fun bind(item: EmptyItemView) {}

}