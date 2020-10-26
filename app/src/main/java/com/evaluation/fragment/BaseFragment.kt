package com.evaluation.fragment

import androidx.fragment.app.Fragment
import com.evaluation.pokemons.model.item.view.types.CategoryView

/**
 * @author Vladyslav Havrylenko
 * @since 24.10.2020
 */
abstract class BaseFragment : Fragment() {

    abstract fun languageLoaded(language: String)

    abstract fun languageSwitched(language: String)

    abstract fun categorySwitched(category: CategoryView)

}