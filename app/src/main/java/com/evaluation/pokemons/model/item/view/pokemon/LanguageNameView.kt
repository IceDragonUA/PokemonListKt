package com.evaluation.pokemons.model.item.view.pokemon

import android.os.Parcelable
import com.evaluation.pokemons.model.item.view.language.LanguageView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageNameView(
    val name: String,
    val language: LanguageView
) : Parcelable