package com.evaluation.utils

import android.widget.ImageView
import android.widget.TextView
import com.evaluation.R
import com.evaluation.glide.GlideApp
import com.evaluation.pokemons.model.item.view.types.CategoryView

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
fun String?.defIfNull() = this ?: ""
fun Int?.defIfNull(def: Int = 0) = this ?: def

fun emptyString() = ""

fun emptyCategory() = CategoryView(name = emptyString(), pokemons = listOf())

fun ImageView.loadFromUrl(url: String) {
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .into(this)
}

fun TextView.initText(text: String) {
    if (text.isNotEmpty()) this.text = text else this.text =
        this.context.applicationContext.getString(R.string.none)
}