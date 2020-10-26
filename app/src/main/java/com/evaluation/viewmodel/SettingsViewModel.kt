package com.evaluation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.pokemons.model.item.view.types.CategoryView

/**
 * @author Vladyslav Havrylenko
 * @since 24.10.2020
 */
class SettingsViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction
) : ViewModel() {

    private var _langResult: LiveData<MutableList<LanguageView>>? = null
    val langResult: LiveData<MutableList<LanguageView>>
        get() {
            if (_langResult == null) {
                _langResult = LiveDataReactiveStreams.fromPublisher(interaction.langList())
            }
            return _langResult ?: throw AssertionError("Set to null by another thread")
        }

    private var _categoryResult: LiveData<MutableList<CategoryView>>? = null
    val categoryResult: LiveData<MutableList<CategoryView>>
        get() {
            if (_categoryResult == null) {
                _categoryResult = LiveDataReactiveStreams.fromPublisher(interaction.categoryList())
            }
            return _categoryResult ?: throw AssertionError("Set to null by another thread")
        }
}